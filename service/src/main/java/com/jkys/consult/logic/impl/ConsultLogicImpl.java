package com.jkys.consult.logic.impl;

import static com.jkys.consult.common.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.statemachine.enums.ConsultEvents.COMPLETE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.CREATE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.TERMINATE;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.db.mybatisplus.component.CustomSequenceGenerator;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.logic.ConsultStateLogic;
import com.jkys.consult.service.ConsultService;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Service
public class ConsultLogicImpl implements ConsultLogic {

  @Resource(name = "consultPersister")
  private StateMachinePersister<ConsultStatus, ConsultEvents, Consult> consultPersister;
//  @Autowired
//  private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister;

  @Autowired
  ConsultStateLogic consultStateLogic;

  @Autowired
  ConsultService consultService;

  @Autowired
  private CustomSequenceGenerator sequenceGenerator;

  @Override
  public String createConsult(Long doctorId, Long patientId, Integer consultType) {
//    StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder
//        .build(beanFactory);

    String consultId;
    try {
      consultId = sequenceGenerator.genBizCode();

      Consult consult = Consult.builder()
          .consultId(consultId)
          .doctorId(doctorId)
          .patientId(patientId)
          .consultType(consultType)
          .build();

      consultService.save(consult);

//        .status(ConsultStatus.INIT).build();
//    stateMachine.getState().getId()

//    Message<ConsultEvents> eventMsg = getMessage(CREATE, consultId);

//    boolean sendResult = stateMachine.sendEvent(eventMsg);
      consultStateLogic.handleAction(CREATE, consultId);
    }catch (Exception e){
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }

    return consultId;
  }

  @Override
  public Boolean terminateConsult(String consultId) {
    try {
      // TODO ---- 触发订单退款操作 ------> todoByliming
      consultStateLogic.handleAction(TERMINATE, consultId);
    }catch (Exception e){
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    return true;
  }

  @Override
  public Boolean completeConsult(String consultId) {
    try {
      consultStateLogic.handleAction(COMPLETE, consultId);
    }catch (Exception e){
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    return true;
  }

//  @Override
//  public Boolean changeDoctor(String consultId, Long doctorId, Long patientId) {
//    try {
//      // TODO ---- 1. 终止原咨询单terminateConsult（包括订单） 2. 开启新咨询单 ------> todoByliming
//      terminateConsult(consultId);
//      // TODO ---- 默认图文 2 ------> todoByliming
//      createConsult(doctorId, patientId, 2);
//    }catch (Exception e){
//      e.printStackTrace();
//      throw new ServerException(SERVER_ERROR, e.getMessage());
//    }
//    return true;
//  }

//  @Override
//  public Boolean wait(String consultId) {
//    return null;
//  }

//  @Override
//  public Boolean startConsult(String consultId) {
//    try {
//      consultStateLogic.handleAction(START, consultId);
//    }catch (Exception e){
//      e.printStackTrace();
//      throw new ServerException(SERVER_ERROR, e.getMessage());
//    }
//    return true;
//  }

}
