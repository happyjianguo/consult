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
   * 当前患者，指定咨询类型和咨询单状态下，咨询单列表
   */
  @Test
  public void testSearchConsultList(){
    long patientId = 2L;
    String consultType = "1";
    String consultState = "待支付";
    BasePage<ConsultInfoModel> page = consultInfoRpcService.searchConsultList(patientId, consultType, consultState);
    Gson gson = new Gson();
    log.info(gson.toJson(page));
  }

  /**
   * 咨询单详情
   */
  @Test
  public void testSearchConsultDetail(){
    ConsultInfoModel result = consultInfoRpcService.searchConsultDetail("WZ202004020000055");
    Gson gson = new Gson();
    log.info(gson.toJson(result));
  }

}