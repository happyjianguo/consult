package com.jkys.consult;

import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.jkys.consult.base.BaseTest;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.common.constants.Constants;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.reponse.ConsultInfoResponse;
import com.jkys.consult.request.ConsultInfoRequest;
import com.jkys.consult.request.PageRequest;
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

  private static Stopwatch stopwatch;

  Gson gson = new Gson();

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
    ConsultInfoRequest request = ConsultInfoRequest.builder()
        .patientId(patientId)
        .consultState("待支付")
        .consultType(Constants.EXPERT_CONSULT_TYPE)
        .build();
    PageRequest page = PageRequest.builder()
        .pageNo(1)
        .pageSize(5)
        .request(request)
        .build();
    log.info(gson.toJson(page));

    BasePage<ConsultInfoModel> basePage = consultInfoRpcService.searchConsultList(page);

    log.info(gson.toJson(basePage));
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

  @Test
  public void testCurrentConsultModelState(){
    ConsultInfoResponse response = consultInfoRpcService.currentConsultModelState(doctorId,patientId, 1);
    log.info(gson.toJson(response));
  }
}