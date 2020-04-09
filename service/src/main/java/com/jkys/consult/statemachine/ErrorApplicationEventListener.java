package com.jkys.consult.statemachine;

import org.springframework.context.ApplicationListener;
import org.springframework.statemachine.event.OnStateMachineError;
import org.springframework.stereotype.Component;

@Deprecated
@Component
public class ErrorApplicationEventListener
    implements ApplicationListener<OnStateMachineError> {

  @Override
  public void onApplicationEvent(OnStateMachineError event) {
    // do something with error
  }
}