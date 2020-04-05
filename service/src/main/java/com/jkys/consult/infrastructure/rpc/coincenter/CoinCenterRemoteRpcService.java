package com.jkys.consult.infrastructure.rpc.coincenter;

import static com.jkys.consult.common.CodeMsg.ASK_PAY_FAIL;

import com.jkys.coincenter.enums.SourceEnum;
import com.jkys.coincenter.rpc.CoinCenterPatientService;
import com.jkys.coincenter.rpc.request.CoinCenterPatientRequest;
import com.jkys.coincenter.rpc.request.CoinRechargeRequest;
import com.jkys.coincenter.rpc.request.QueryOrderPayResultRequest;
import com.jkys.coincenter.rpc.response.CoinCenterPatientResponse;
import com.jkys.coincenter.rpc.response.CoinRechargeResponse;
import com.jkys.coincenter.rpc.response.QueryOrderPayResultModel;
import com.jkys.coincenter.rpc.response.QueryOrderPayResultResponse;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.bean.PatientAdvisoryInfo;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.rpc.chat.ChatMessageService;
import com.jkys.consult.shine.bean.PatientAdvisoryOrder;
import com.jkys.consult.shine.mapper.DoctorAdvisoryPriceMapper;
import com.jkys.consult.shine.mapper.PatientAdvisoryInfoMapper;
import com.jkys.consult.shine.service.AppConfig;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CoinCenterRemoteRpcService {

  @Resource
  private AppConfig appConfig;
  @Resource
  private PatientAdvisoryInfoMapper patientAdvisoryInfoMapper;
  @Resource
  private ChatMessageService chatMessageService;
  @Resource
  private CoinCenterPatientService coinCenterPatientService;
  @Resource
  private DoctorAdvisoryPriceMapper doctorAdvisoryPriceMapper;

  private final static Object lock = "lock";

  //云币中心加云币
  Boolean isIncreaseCoin(PatientAdvisoryOrder order) {
    if (order.getCostCoin() == 0) {
      return true;
    }
    try {
      CoinCenterPatientRequest request = new CoinCenterPatientRequest();
      request.setCoinCount(order.getCostCoin());
      request.setUserId(order.getPatientId());
      request.setSeriablize(order.getBizCode());
      request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
      CoinCenterPatientResponse res = coinCenterPatientService.increasePatientCoin(request);
      log.info("isIncreaseCoin number:{},订单：{}，金额：{}", order.getOrderNum(), order.getBizCode(),
          order.getCostCoin());
      if (res == null || !res.getSuccess()) {
        log.warn("isIncreaseCoin false Exception orderNum:{}", order.getOrderNum());
        return false;
      }
      return true;
    } catch (Exception e) {
      log.error("isIncreaseCoin error orderNum:{}", order.getOrderNum(), e);
    }
    return false;
  }

  //云币中心扣云币
  public Boolean isDecreasePatientCoin(Order order, Integer coin) {
    if (coin == 0) {
      return true;
    }
    try {
      CoinCenterPatientRequest request = new CoinCenterPatientRequest();
      request.setCoinCount(coin);
      request.setUserId(order.getPatientId());
      request.setSeriablize(order.getOrderId());
      request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
      CoinCenterPatientResponse res = coinCenterPatientService.decreasePatientCoin(request);
      if (res == null || !res.getSuccess()) {
        log.warn("isDecreasePatientCoin false  orderNum:{}", order.getOrderId());
        return false;
      }
      log.info("isDecreasePatientCoin result orderNum:{},Seriablize:{},{}", order.getOrderId(),
          res.getSuccess());
      return res.getSuccess();
    } catch (Exception e) {
      log.error("isDecreasePatientCoin error orderNum:{}", order.getOrderId(), e);
    }
    return false;
  }

  //云币中心查询处理结果
  public Boolean hasSuccessHandle(PatientAdvisoryOrder order) {
    try {
      CoinCenterPatientRequest request = new CoinCenterPatientRequest();
      request.setUserId(order.getPatientId());
      request.setSeriablize(order.getBizCode());
      request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
      CoinCenterPatientResponse res = coinCenterPatientService.hasSuccessHandle(request);
      if (res == null || !res.getSuccess()) {
        log.warn("hasSuccessHandle false Exception orderNum:{}", order.getOrderNum());
        return false;
      }
      return true;
    } catch (Exception e) {
      log.error("hasSuccessHandle error orderNum:{}", order.getOrderNum(), e);
    }
    return false;
  }

  /**
   * 根据订单ID判断是否支付成功
   *
   * @param orderNum 跟下单的id保持一致
   */
  public boolean isPaySuccess(String orderNum) {
    QueryOrderPayResultRequest request = new QueryOrderPayResultRequest();
    request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
    List<String> orderId = new ArrayList<>(1);
    orderId.add(orderNum);
    request.setOrderIdList(orderId);

    log.info("验证支付:是否成功，orderNum:{}", orderNum);
    QueryOrderPayResultResponse response = coinCenterPatientService.queryOrderPayResult(request);
    if (response != null && response.getSuccess() && response.getPayList() != null &&
        !response.getPayList().isEmpty()) {
      QueryOrderPayResultModel resultModel = response.getPayList().get(0);
      // 云币数为0，代表没有充值成功
      if (resultModel.getCoinCount() == 0) {
        log.info("验证支付充值失败，orderNum:{}", orderNum);
        return false;
      } else {
        log.info("验证支付充值成功，orderNum:{}", orderNum);
        return true;
      }
    }
    return false;
  }

  public CoinRechargeResponse coinRecharge(Order order, Integer coin,
      Integer payWay) {
    // 调用支付串rpc
    CoinRechargeRequest request = new CoinRechargeRequest();
    request.setOrderId(order.getOrderId());
    request.setPayWay(payWay);
    request.setUserId(order.getPatientId());
    request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
    request.setCoinCount(coin);
    log.info("RPC 获取支付串：userId:{} orderId:{} coinCount:{}", order.getPatientId(),
        order.getOrderId(), coin);

    CoinRechargeResponse response = coinCenterPatientService.coinRecharge(request);
    log.info("咨询订单获取支付串,返回结果：result={}", response == null ? null : response.getSuccess());
    if (response == null || !response.getSuccess() || response.getPayString() == null) {
      throw new ServerException(ASK_PAY_FAIL);
    }
    return response;
  }

  /**
   * 重新获取支付串
   */
  public CoinRechargeResponse reacquirePayString(Order order, int payWay) {
    CoinRechargeRequest request = new CoinRechargeRequest();
    request.setOrderId(order.getOrderId());
    request.setPayWay(payWay);
    request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
    log.info("RPC 重新获取支付串：userId:{} orderId:{}", order.getPatientId(), order.getOrderId());

    CoinRechargeResponse response = coinCenterPatientService.reacquirePayString(request);
    log.info("RPC 重新获取支付串,返回结果：result={}", response == null ? null : response.getSuccess());
    if (response == null || !response.getSuccess() || response.getPayString() == null) {
      throw new ServerException(ASK_PAY_FAIL);
    }
    return response;
  }

  // TODO ---- 医生价格 ------> todoByliming
  public Integer doctorPrice(Long doctorId) {
    Integer price = doctorAdvisoryPriceMapper.queryDoctorAdvisoryPrice(doctorId);
    return price != null ? price : appConfig.getCostCoin();
  }

  public Integer doctorOrigin(Long doctorId) {
    Integer price = doctorAdvisoryPriceMapper.queryDoctorAdvisoryPrice(doctorId);
    return price != null ? price : appConfig.getOriginalCoin();
  }

  /**
   * 更新未回复的时间信息
   */
  public void updateNoRepeatWaitingTime(PatientAdvisoryInfo info) {
    Long firstTime = chatMessageService
        .queryFirstMessageDateTime(info.getDoctorId(), info.getPatientId(),
            info.getStartTime());
    PatientAdvisoryInfo updateInfo = new PatientAdvisoryInfo();
    final Long nd = 1000 * 24 * 60 * 60L; //一天
    final long nh = 1000 * 60 * 60L;//一小时
    final long nm = 1000 * 60L;//一分钟
    Long diff = firstTime > 0 && firstTime > info.getStartTime().getTime() ?
        firstTime - info.getStartTime().getTime()
        : System.currentTimeMillis() - info.getStartTime().getTime();
    //转换为10.44形式的值，将天转为小时，整数为小时，小数为分钟
    updateInfo.setWaiting((diff / nd * 24d) + diff % nd / nh + (diff % nd % nh / nm / 100d));
    if (firstTime > info.getStartTime().getTime()) {
      updateInfo.setRepeatStatus(1);
    }
    updateInfo.setId(info.getId());
    patientAdvisoryInfoMapper.updateOrderUserInfo(updateInfo);
  }

}
