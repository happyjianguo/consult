package com.jkys.consult.logic.impl;

import static com.jkys.consult.common.CodeMsg.COIN_EXIST;
import static com.jkys.consult.common.CodeMsg.INVALID_ORDER;
import static com.jkys.consult.common.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.shine.enums.ExceptionMessage.COIN_FAIL;

import com.alibaba.fastjson.JSONObject;
import com.jkys.coincenter.enums.SourceEnum;
import com.jkys.coincenter.rpc.CoinCenterPatientService;
import com.jkys.coincenter.rpc.request.CoinCenterPatientRequest;
import com.jkys.coincenter.rpc.response.CoinCenterPatientResponse;
import com.jkys.coincenter.rpc.response.CoinRechargeResponse;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.bean.PatientAdvisoryInfo;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.rpc.chat.ChatMessageService;
import com.jkys.consult.infrastructure.rpc.coincenter.CoinCenterRemoteRpcService;
import com.jkys.consult.logic.PayInfoLogic;
import com.jkys.consult.model.CoinOrderModel;
import com.jkys.consult.request.OrderPayRequest;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.shine.service.MqServer;
import com.jkys.consult.shine.service.RedisService;
import com.jkys.consult.shine.service.UserCenterUserService;
import com.jkys.consult.statemachine.enums.OrderStatus;
import com.jkys.phobos.ApiGateway;
import com.jkys.pt.client.service.member.MemberAdvisoryRpcService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author yangZh
 * @since 2018/7/13
 **/
@Slf4j
@Service
public class PayInfoLogicImpl implements PayInfoLogic {

  @Resource
  private OrderService orderService;
  @Resource
  private ChatMessageService chatMessageService;
  @Resource
  private CoinCenterPatientService coinCenterPatientService;
  @Resource
  private CoinCenterRemoteRpcService coinCenterRemoteRpcService;
  @Resource
  private RedisService redisService;
  @Resource
  private MqServer mqServer;
  @Resource
  private UserCenterUserService userCenterUserService;
  @Resource
  private MemberAdvisoryRpcService memberAdvisoryRpcService;

  private static final Integer REDIS_TIMEOUT = 10;//重复发起支付间隔 10秒
  private static final Long PAY_TIMEOUT = 1000L; //自循环间隔时间 毫秒

  public Integer getMemberFree(Long patientId, Long doctorId){
    Integer memberFree = 0;
    if (userCenterUserService.isMember(patientId)) {
      memberFree = memberAdvisoryRpcService
          .getFreeAdvisoryByDocId(patientId, doctorId);
    }
    return memberFree;
  }

  /**
   * 可重入方法,无事务，多次支付优先查找是否已支付糖币成功
   *
   * @param request 使用 医生id  病人id
   */
  @Override
  public CoinOrderModel payGo(OrderPayRequest request) {
    // TODO ---- 测试用 ------> todoByliming
    if(request.getMock()) {
      return new CoinOrderModel();
    }

    // TODO ---- 查找咨询单 ------> todoByliming
    // TODO 最后需要用网关
//    Long patientId = ApiGateway.getUserId();
    Long patientId = request.getPatientId();
    String orderId = request.getOrderId();
    Long doctorId = request.getDoctorId();

    Order order = orderService.selectByOrderId(orderId);

    Integer price = order.getPrice();

    Integer memberFree = getMemberFree(patientId, doctorId);
    if (memberFree != null && memberFree > 0) {
      price = 0;
    }

    CoinOrderModel model = new CoinOrderModel();
    boolean success = false;
    model.setPrice(price);
    int times = 0;
    while (times < 3) {
      times++;
      //处理云币余额扣款
      Integer coin = coinCount(patientId);
      model.setCoin(coin);
      if (coin < price) {
        model.setCoinPay(2);//余额不足
      }
      if (coin >= price) {
        if (!coinCenterRemoteRpcService.isDecreasePatientCoin(order, price)) {
          throw new ServerException(SERVER_ERROR, COIN_FAIL);
        }
        model.setCoinPay(1);
        success = true;
        model.setCoin(coin - price);
        break;
      }
      if (!request.getReTry()) {
        break;
      }
      try {
        Thread.sleep(PAY_TIMEOUT);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    PatientAdvisoryInfo info = null;
    //        PatientAdvisoryInfo info = patientAdvisoryInfoMapper.findByDocAndPatient(request.getDoctorId(), patientId);
    if (success) {
      if (memberFree != null && memberFree > 0) {
        boolean member = memberAdvisoryRpcService.addFreeAdvisory(info.getId(), patientId,
            info.getDoctorId(), request.getClient());
        if (!member) {
          throw new ServerException(SERVER_ERROR, "请检查您的会员咨询权限或退出重试!");
        }
      }

      // TODO ---- 12和24小时以支付时间为准，也就是订单支付状态PAYED ------> todoByliming
//      patientAdvisoryInfoMapper.updateOrderStatusStart(info.getId(),
//          System.currentTimeMillis() + Constants.EXPIRE_TIME);
//                patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.PAY_SUCCESS.getCode(), order.getBizCode());

      chatMessageService.sendOrderMessage(info.getPatientId(), info.getDoctorId(), info.getId());

    }
//        } catch (ShineServiceException s) {
//            log.warn("确认支付 发生错误，doctorId:{},patientId:{},{}", request.getDoctorId(), patientId, s.getMessage());
//            throw s;
//        } catch (Exception e) {
//            log.error("确认支付 订单异常，patient= {}", patientId, e);
//            throw new ShineServiceException(ExceptionCode.FAIL);
//        } finally {
//            if (model.getCoinPay() == 0 && order != null) {
//                log.info("确认支付 支付失败，doctorId:{},patientId:{},orderNum:{}", request.getDoctorId(), patientId,
//                        order.getOrderNum());
//                patientAdvisoryOrderMapper.updateOrderStatus(OrderStatus.PAY_FAIL.getCode(), order.getBizCode());
//
//            }
//        }
//        }
//    model.setId(order.getId());

    // TODO ---- 捕获异常，支付异常处理 ------> todoByliming
    //            log.warn("确认支付 发生错误，doctorId:{},patientId:{},{}", request.getDoctorId(), patientId, s.getMessage());

    model.setOrderId(order.getOrderId());
//    model.setInfoId(info.getId());
//    model.setBizCode(order.getConsultId());
//    model.setSuccess(true);
    log.info("确认支付 返回结果，result:{}", JSONObject.toJSONString(model));
    return model;
  }

  @Override
  public CoinOrderModel queryCoin(Long userId, Long doctorId) {
    log.info("咨询订单用户云币查询查询 请求开始，patientId:{},doctorId:{}", userId, doctorId);
    Long patientId = ApiGateway.getUserId();
    if (doctorId == null || patientId == null) {
      log.warn("咨询订单用户云币查询查询：参数异常！");
      throw new ServerException(SERVER_ERROR, "咨询订单用户云币查询：参数异常！");
    }
    CoinOrderModel model = new CoinOrderModel();
    try {
      //云币中心获取云币余额
      model.setCoin(coinCount(patientId));
      model.setPrice(coinCenterRemoteRpcService.doctorPrice(doctorId));
//      model.setOriginalCoin(coinCenterRemoteRpcService.doctorOrigin(doctorId));
//      model.setSuccess(true);
    } catch (Exception e) {
//            throw new ShineServiceException(ExceptionCode.FAIL);
      throw new ServerException(SERVER_ERROR, String.format("咨询订单用户云币查询：%s", e.getMessage()));
    }
    return model;
  }

  @Override
  public CoinOrderModel payString(String bizCode, int payWay) {
    log.info("咨询订单获取支付串：orderNum={}，payWay={}", bizCode, payWay);
    if (StringUtils.isEmpty(bizCode) || ApiGateway.getUserId() == null) {
      log.warn("咨询订单支付糖币：参数异常！");
      throw new ServerException(SERVER_ERROR, "咨询订单支付糖币：参数异常！");
    }
    CoinOrderModel model = new CoinOrderModel();
//    model.setSuccess(false);
//        try {
    Order order = orderService.selectByOrderId(bizCode);
    // TODO ---- 支付中 ------> todoByliming
    if (order == null || order.getStatus().getCode() > OrderStatus.PAYING.getCode() ||
        !ApiGateway.getUserId().equals(order.getPatientId())) {
      throw new ServerException(INVALID_ORDER);
    }
    // 云币中心获取余额
    Integer coin = coinCount(order.getPatientId());
//            Integer price = coinCenterRemoteRpcService.doctorPrice(order.getDoctorId());
    if (coin >= order.getPrice()) {
      // TODO 再看看是否这么处理
      throw new ServerException(COIN_EXIST);
    }
    Integer cost = order.getPrice() - coin;
    CoinRechargeResponse response;
    // TODO ---- 支付中 ------> todoByliming
    if (OrderStatus.PAYING.getCode() == order.getStatus().getCode() &&
        !coinCenterRemoteRpcService.isPaySuccess(order.getOrderId())) {
      log.info("使用原来的订单号和支付方式，调用云币中心的接口，从第三方重新获取payString：{}", bizCode);
      response = coinCenterRemoteRpcService.reacquirePayString(order, payWay);
    } else {
      response = coinCenterRemoteRpcService.coinRecharge(order, cost, payWay);
      // TODO ---- 更新状态 ------> todoByliming
      orderService.updateOrderPaying(cost, bizCode, response.getPayString());
//      orderService.updateOrderPaying(cost, bizCode, response.getPayString());
    }
//    model.setBizCode(bizCode);
    model.setPayString(response.getPayString());
//    model.setSuccess(true);
        /*} catch (ShineServiceException s) {
            log.warn("咨询订单获取支付串 业务异常，bizCode:{},{}", bizCode, s.getMessage());
            throw new ShineServiceException(Integer.valueOf(s.getCode()), s.getMessage());
        } catch (Exception e) {
            log.error("咨询订单获取支付串 异常，bizCode {}", bizCode, e);
            throw new ShineServiceException(ExceptionCode.FAIL);
        }*/

    return model;
  }

  /**
   * 云币中心获取云币
   *
   * @param userId 用户id
   * @return 糖币余额
   */
  private Integer coinCount(Long userId) {
    CoinCenterPatientRequest request = new CoinCenterPatientRequest();
    request.setUserId(userId);
    request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
    Integer coin = 0;
    try {
      CoinCenterPatientResponse response = coinCenterPatientService.findPatientCoinCount(request);
      if (response != null && response.getCoinCount() != null) {
        coin = response.getCoinCount();
      }
      log.info("云币中心获取云币 结果，userId:{},{}", userId, coin);
    } catch (Exception e) {
      log.error("云币中心获取云币 异常，userId= {}", userId, e);
    }
    return coin;
  }

//    @Override
//    public List<CoinOrderModel> batchQueryAdvisoryPrice(List<Long> doctorIds) {
//        log.info("批量查询医生咨询价格 请求开始，doctorId:{}", doctorIds);
//
//        if (doctorIds == null || doctorIds.isEmpty()) {
//            log.warn("批量查询医生咨询价格：参数为空！");
//            throw new ServerException(SERVER_ERROR, "批量查询医生咨询价格：参数为空！");
//        }
//
//        return coinCenterRemoteRpcService.batchQueryAdvisoryPrice(doctorIds);
//    }
}
