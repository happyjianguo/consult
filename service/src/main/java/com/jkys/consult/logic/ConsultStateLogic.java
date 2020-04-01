package com.jkys.consult.logic;

import static com.jkys.consult.statemachine.utils.MessageUtil.getMessage;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.service.ConsultService;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import com.jkys.consult.statemachine.handler.ConsultPersistStateMachineHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ConsultStateLogic {
  @Autowired
  private ConsultPersistStateMachineHandler handler;

  @Autowired
  ConsultService ConsultService;

  public boolean handleAction(ConsultEvents event, Consult consult) {
    String consultId = consult.getConsultId();
    Consult result = ConsultService.selectByConsultId(consultId);

    //发送事件去触发状态机
    return handler.handleEventWithState(getMessage(event, consultId),
        ObjectUtils.isEmpty(result) ? ConsultStatus.INIT : result.getStatus());
  }
}