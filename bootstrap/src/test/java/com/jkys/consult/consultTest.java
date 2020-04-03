package com.jkys.consult;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.jkys.consult.base.BaseTest;
import com.jkys.consult.common.BasePage;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.service.consult.ConsultInfoRpcService;
import com.jkys.consult.service.order.OrderInfoRpcService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class consultTest extends BaseTest {

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
   * 创建咨询单，同时创建订单
   */
  @Test
  public void testCreateConsult(){
    long patientId = 2L;
    String consultType = "1";
    String consultState = "待支付";
    BasePage<ConsultInfoModel> page = consultInfoRpcService.searchConsultList(patientId, consultType, consultState);
    Gson gson = new Gson();
    log.info(gson.toJson(page));
  }

  /**
   * 完成咨询单
   */
  @Test
  public void testCompleteConsult(){
    boolean result = consultInfoRpcService.completeConsult(consultId);
    Assert.assertEquals(result, true);
  }

}