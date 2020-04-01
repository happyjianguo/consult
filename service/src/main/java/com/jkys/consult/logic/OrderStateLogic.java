package com.jkys.consult.logic;

import static com.jkys.consult.statemachine.utils.MessageUtil.getMessage;

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

  public boolean handleAction(OrderEvents event, Order order) {
    String orderId = order.getOrderId();
    Order result = OrderService.selectByOrderId(orderId);

    //发送事件去触发状态机
    return handler.handleEventWithState(getMessage(event, orderId),
        ObjectUtils.isEmpty(result) ? OrderStatus.INIT : result.getStatus());
  }
}
