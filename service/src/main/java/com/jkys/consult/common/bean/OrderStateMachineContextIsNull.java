package com.jkys.consult.common.bean;

import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.enums.OrderStatus;
import org.springframework.statemachine.support.DefaultStateMachineContext;

public class OrderStateMachineContextIsNull extends
    DefaultStateMachineContext<OrderStatus, OrderEvents> {

  public OrderStateMachineContextIsNull() {
    super(null, null, null, null, null, null, null);
  }
}
