package com.jkys.consult;

import com.jkys.consult.base.BaseTest;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.service.consult.ConsultInfoRpcService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class stateTest extends BaseTest {

  @Autowired
  ConsultLogic consultLogic;

  @Autowired
  ConsultInfoRpcService consultInfoRpcService;

  private String consultId;

  private String orderId;

  /**
   * 创建咨询单，同时创建订单
   */
  @Test
  public void testCreateConsult(){
    long doctorId = 1L;
    long patientId = 2L;
    int consultType = 1;
    String consultId = consultLogic.createConsult(doctorId, patientId, consultType);
    boolean result = Optional.ofNullable(consultId).isPresent();
    Assert.assertEquals(result, true);
  }

}