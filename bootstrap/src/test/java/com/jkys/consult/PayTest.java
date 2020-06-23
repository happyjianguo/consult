package com.jkys.consult;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.jkys.coincenter.enums.SourceEnum;
import com.jkys.coincenter.rpc.CoinCenterPatientService;
import com.jkys.coincenter.rpc.request.CoinCenterPatientRequest;
import com.jkys.coincenter.rpc.response.CoinCenterPatientResponse;
import com.jkys.consult.base.BaseTest;
import com.jkys.consult.logic.PayInfoLogic;
import com.jkys.consult.reponse.PayOrderResponse;
import com.jkys.consult.request.OrderPayRequest;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PayTest extends BaseTest {

  @Autowired
  PayInfoLogic payInfoLogic;

  @Resource
  CoinCenterPatientService coinCenterPatientService;

  private static Stopwatch stopwatch;

  @BeforeClass
  public static void setUp() {
    stopwatch = Stopwatch.createStarted();
  }

  @AfterClass
  public static void afterAll() {
    log.error(stopwatch.toString());
  }

  /**
   * 支付订单，同时启用咨询单
   */
  @Test
  public void testPayOrder() {
    OrderPayRequest request = OrderPayRequest.builder()
        .mock(true)
        .orderId("WZ202004080000084")
        // TODO ----  ------> todoByliming
//        .doctorId(doctorId)
        .patientId(patientId)
//        .client()
//        .reTry()
        .build();
    PayOrderResponse result = payInfoLogic.payGo(request);
    Gson gson = new Gson();
    gson.toJson(result);
  }

  @Test
  public void testCoinCount(){
    CoinCenterPatientRequest request = new CoinCenterPatientRequest();
    request.setUserId(patientId);
    request.setSource(SourceEnum.ORDER_ADVISORY.getSource());
    Integer coin = 0;
    try {
      CoinCenterPatientResponse response = coinCenterPatientService.findPatientCoinCount(request);
      if (response != null && response.getCoinCount() != null) {
        coin = response.getCoinCount();
      }
      log.info("云币中心获取云币 结果，userId:{},{}", patientId, coin);
    } catch (Exception e) {
      log.error("云币中心获取云币 异常，userId= {}", patientId, e);
    }
  }
}