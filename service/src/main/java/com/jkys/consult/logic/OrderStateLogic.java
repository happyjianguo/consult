package com.jkys.consult.logic;

import static com.jkys.consult.statemachine.utils.MessageUtil.getMessage;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.enums.OrderStatus;
import com.jkys.consult.statemachine.handler.OrderPersistStateMachineHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class OrderStateLogic {

  @Autowired
  private OrderPersistStateMachineHandler handler;

  @Autowired
  OrderService OrderService;

  public boolean handleAction(OrderEvents event, Consult consult) {
    Order result = OrderService.selectByConsultId(consult.getConsultId());

    return handleState(event, result);
  }

  public boolean handleAction(OrderEvents event, Order order) {
    Order result = OrderService.selectByOrderId(order.getOrderId());

    return handleState(event, result);
  }

  public boolean handleState(OrderEvents event, Order result) {
    //发送事件去触发状态机
    return handler.handleEventWithState(getMessage(event, result),
        ObjectUtils.isEmpty(result) ? OrderStatus.INIT : result.getStatus());
  }
}
