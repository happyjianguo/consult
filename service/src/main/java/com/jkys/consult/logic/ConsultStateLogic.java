package com.jkys.consult.logic;

import static com.jkys.consult.statemachine.utils.MessageUtil.getMessage;

import com.jkys.consult.service.ConsultService;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.handler.ConsultPersistStateMachineHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultStateLogic {
  @Autowired
  private ConsultPersistStateMachineHandler handler;

  @Autowired
  ConsultService ConsultService;

  public boolean handleAction(ConsultEvents event, String consultId) {
//    Optional<Consult> Consult = Optional.ofNullable(ConsultService.selectByConsultId(consultId));

    //发送事件去触发状态机
    return handler.handleEventWithState(getMessage(event, consultId)/*,
        Consult.isPresent() ? Consult.get().getStatus() : ConsultStatus.INIT*/);
  }
}
