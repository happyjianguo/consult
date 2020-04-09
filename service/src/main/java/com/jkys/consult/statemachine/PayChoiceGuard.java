package com.jkys.consult.statemachine;

import com.jkys.consult.common.bean.Order;
import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.enums.OrderStatus;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

/**
 * CHECK_CHOICE guard
 */
public class PayChoiceGuard implements Guard<OrderStatus, OrderEvents> {

  @Override
  public boolean evaluate(StateContext<OrderStatus, OrderEvents> context) {
    System.out.println("PayChoiceGuard!!!!!!!!!!!!!");
    Order order = (Order) context.getMessageHeader(Constants.ORDER);
//
    boolean returnValue = false;
//    Form form = context.getMessage().getHeaders().get("form", Form.class);
//    returnValue = form.getFormName() != null;
//    System.out.println(form.toString() + " is " + returnValue);
    return returnValue;
  }

}
