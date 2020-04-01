package com.jkys.consult.common.bean;

import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import org.springframework.statemachine.support.DefaultStateMachineContext;

public class ConsultStateMachineContextIsNull extends
    DefaultStateMachineContext<ConsultStatus, ConsultEvents> {

  public ConsultStateMachineContextIsNull() {
    super(null, null, null, null, null, null, null);
  }
}
