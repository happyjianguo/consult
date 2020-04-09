package com.jkys.consult.logic.impl;

import static com.jkys.consult.common.component.CodeMsg.COIN_EXIST;
import static com.jkys.consult.common.component.CodeMsg.INVALID_ORDER;
import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.common.constants.Constants.COIN_FAIL;
import static com.jkys.consult.common.constants.Constants.COIN_INSUFFICIENT;
import static com.jkys.consult.common.constants.Constants.COIN_SUCCESS;
import static com.jkys.consult.common.constants.Constants.RETRY_TIMES_4_PAY;
import static com.jkys.consult.common.constants.Constants.ZERO;

import com.google.gson.Gson;
import com.jkys.coincenter.rpc.CoinCenterPatientService;
import com.jkys.coincenter.rpc.response.CoinRechargeResponse;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.constants.Constants;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.rpc.chat.ChatMessageService;
import com.jkys.consult.infrastructure.rpc.coincenter.CoinCenterRemoteRpcService;
import com.jkys.consult.infrastructure.rpc.member.MemberService;
import com.jkys.consult.infrastructure.rpc.usercenter.UserCenterUserService;
import com.jkys.consult.logic.DoctorIncomeLogic;
import com.jkys.consult.logic.PayInfoLogic;
import com.jkys.consult.reponse.PayOrderResponse;
import com.jkys.consult.request.OrderPayRequest;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.shine.service.MqServer;
import com.jkys.consult.shine.service.RedisService;
import com.jkys.consult.statemachine.enums.OrderStatus;
import com.jkys.phobos.ApiGateway;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 支付订单
 */
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
  private UserCenterUserService userCenterUserService;
  @Resource
  private MemberService memberService;
  @Resource
  private RedisService redisService;
  @Resource
  private MqServer mqServer;

  private static final Integer REDIS_TIMEOUT = 10;//重复发起支付间隔 10秒
  private static final Long PAY_TIMEOUT = 1000L; //自循环间隔时间 毫秒

  private PayOrderResponse deductMemberFreeTimes(Order order, OrderPayRequest request) {

    // TODO ---- 等 谢春为 添加完会员权益接口 ------> todoByliming
    Long patientId = order.getPatientId();
    Long doctorId = order.getDoctorId();
    String client = request.getClient();

    boolean deductMemberFreeTimes = memberService
        .deductMemberFreeTimes(order.getOrderId(), patientId, doctorId, client, request.getMock());

    if (deductMemberFreeTimes) {
      return PayOrderResponse.builder()
          .coinPay(COIN_SUCCESS)
          .consultId(order.getConsultId())
          .orderId(order.getOrderId())
          .price(ZERO)
          .build();
    } else {
      return null;
    }
  }

  private PayOrderResponse deductCoin(Order order, Integer coin, Integer reTryTimes,
      Integer coinPay) {
    Integer price = order.getPrice();
    if (coin < price) {
      coinPay = COIN_INSUFFICIENT;//余额不足
    }
    try {
      if (coin >= price) {
        if (!coinCenterRemoteRpcService.isDecreasePatientCoin(order)) {
          coinPay = COIN_FAIL;
//          throw new ServerException(SERVER_ERROR, COIN_FAIL);
        } else {
          coinPay = COIN_SUCCESS;
        }
      }
      Thread.sleep(PAY_TIMEOUT);
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }

    if (!coinPay.equals(Constants.COIN_FAIL) || --reTryTimes < 0) {
      coin = coinPay.equals(COIN_SUCCESS) ? coin - price : coin;
      return PayOrderResponse.builder()
          .consultId(order.getConsultId())
          .orderId(order.getOrderId())
          .coinPay(coinPay)
          .coin(coin - price)
          .price(price)
          .build();
    } else {
      return deductCoin(order, coin, reTryTimes, coinPay);
    }
  }

  private PayOrderResponse deductCoin(Order order, OrderPayRequest request) {
    Long patientId = order.getPatientId();
    int reTryTimes = request.getReTry() ? ZERO : RETRY_TIMES_4_PAY;

    // 获取云币余额
    Integer coin = coinCenterRemoteRpcService.coinCount(patientId);

    Integer coinPay = COIN_FAIL;
    return deductCoin(order, coin, reTryTimes, coinPay);
  }

  /**
   * 可重入方法,无事务，多次支付优先查找是否已支付糖币成功
   *
   * @param request 使用 医生id  病人id
   */
  @Override
  public PayOrderResponse payGo(OrderPayRequest request) {
    // TODO ---- 查找咨询单 ------> todoByliming
    // TODO 最后需要用网关
//    Long patientId = ApiGateway.getUserId();

    Order order = orderService.selectByOrderId(request.getOrderId());

    PayOrderResponse model;

    try {
      model = deductMemberFreeTimes(order, request);

      if (ObjectUtils.isEmpty(model)) {
        model = deductCoin(order, request);
      }
    } catch (ServerException e) {
      log.warn("确认支付 发生错误，doctorId:{},patientId:{},{}", order.getDoctorId(), order.getPatientId(),
          e.getMessage());
      throw e;
    } catch (Exception e) {
      log.warn("确认支付 发生错误，doctorId:{},patientId:{},{}", order.getDoctorId(), order.getPatientId(),
          e.getMessage());
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }

    Gson gson = new Gson();
    log.info("确认支付 返回结果，result:{}", gson.toJson(model));
    return model;
  }

  @Autowired
  DoctorIncomeLogic doctorIncomeLogic;

  @Override
  @Deprecated
  public PayOrderResponse queryCoin(Long patientId, Long doctorId) {
    log.info("咨询订单用户云币查询查询 请求开始，patientId:{},doctorId:{}", patientId, doctorId);
    patientId = ObjectUtils.isEmpty(patientId) ? ApiGateway.getUserId() : patientId;
    if (doctorId == null || patientId == null) {
      log.warn("咨询订单用户云币查询查询：参数异常！");
      throw new ServerException(SERVER_ERROR, "咨询订单用户云币查询：参数异常！");
    }
    PayOrderResponse model = new PayOrderResponse();
    try {
      //云币中心获取云币余额
      model.setCoin(coinCenterRemoteRpcService.coinCount(patientId));
//      model.setPrice(coinCenterRemoteRpcService.doctorPrice(doctorId));
      model.setPrice(doctorIncomeLogic.getDoctorPrice(doctorId));
//      model.setOriginalCoin(coinCenterRemoteRpcService.doctorOrigin(doctorId));
//      model.setSuccess(true);
    } catch (Exception e) {
//            throw new ShineServiceException(ExceptionCode.FAIL);
      throw new ServerException(SERVER_ERROR, String.format("咨询订单用户云币查询：%s", e.getMessage()));
    }
    return model;
  }

  @Override
  public PayOrderResponse payString(String bizCode, int payWay) {
    log.info("咨询订单获取支付串：orderNum={}，payWay={}", bizCode, payWay);
    if (StringUtils.isEmpty(bizCode) || ApiGateway.getUserId() == null) {
      log.warn("咨询订单支付糖币：参数异常！");
      throw new ServerException(SERVER_ERROR, "咨询订单支付糖币：参数异常！");
    }
    PayOrderResponse model = new PayOrderResponse();
//    model.setSuccess(false);
//        try {
    Order order = orderService.selectByOrderId(bizCode);
    // TODO ---- 支付中 ------> todoByliming
    if (order == null || order.getStatus().getCode() > OrderStatus.PAYING.getCode() ||
        !ApiGateway.getUserId().equals(order.getPatientId())) {
      throw new ServerException(INVALID_ORDER);
    }
    // 云币中心获取余额
    Integer coin = coinCenterRemoteRpcService.coinCount(order.getPatientId());
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
