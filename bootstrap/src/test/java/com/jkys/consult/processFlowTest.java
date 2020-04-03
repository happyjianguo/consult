package com.jkys.consult;

import com.google.common.base.Stopwatch;
import com.jkys.consult.base.BaseTest;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.service.consult.ConsultInfoRpcService;
import com.jkys.consult.service.order.OrderInfoRpcService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class processFlowTest extends BaseTest {

  @Autowired
  ConsultLogic consultLogic;

  @Autowired
  ConsultInfoRpcService consultInfoRpcService;

  @Autowired
  OrderInfoRpcService orderInfoRpcService;

  private String consultId;

  private String orderId;

  private static Stopwatch stopwatch;

  @BeforeEach
  void setUp() {
    stopwatch = Stopwatch.createStarted();
  }

  @AfterAll
  static void afterAll() {
    log.error(stopwatch.toString());
  }

  /**
   * 正常完结
   */
  @Test
  public void testNomalPAY(){
    testCreateConsult();
    testPayOrder();
    testCompleteConsult();
  }

  /**
   * 到时中止，退款
   */
  @Test
  public void testTerminate(){
    testCreateConsult();
    testPayOrder();
    testTerminateConsult();
  }

  /**
   * 取消订单
   */
  @Test
  public void testNoPAY(){
    testCreateConsult();
    testCancelOrder();
  }

  /**
   * 创建咨询单，同时创建订单
   */
  @Test
  public void testCreateConsult(){
    long doctorId = 1L;
    long patientId = 2L;
    int consultType = 1;
    consultId = consultLogic.createConsult(doctorId, patientId, consultType);
    // TODO ---- orderId = consultId; ------> todoByliming
    orderId = consultId;
    boolean result = Optional.ofNullable(consultId).isPresent();
    Assert.assertEquals(result, true);
  }

  /**
   * 完成咨询单
   */
  @Test
  public void testCompleteConsult(){
    boolean result = consultInfoRpcService.completeConsult(consultId);
    Assert.assertEquals(result, true);
  }

  /**
   * 中止咨询单
   */
  @Test
  public void testTerminateConsult(){
    boolean result = consultLogic.terminateConsult(consultId);
    Assert.assertEquals(result, true);
  }

  /**
   * 支付订单，同时启用咨询单
   */
  @Test
  public void testPayOrder(){
    boolean result = orderInfoRpcService.payOrder(orderId);
    Assert.assertEquals(result, true);
  }

  /**
   * 取消订单，同时取消咨询单
   */
  @Test
  public void testCancelOrder(){
    boolean result = orderInfoRpcService.cancelOrder(orderId);
    Assert.assertEquals(result, true);
  }

}