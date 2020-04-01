package com.jkys.consult.logic;

import static com.jkys.consult.statemachine.utils.MessageUtil.getMessage;

import com.jkys.consult.common.bean.Order;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.handler.OrderPersistStateMachineHandler;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStateLogic {
  @Autowired
  private OrderPersistStateMachineHandler handler;

  @Autowired
  OrderService OrderService;

  public boolean handleAction(OrderEvents event, Order order) {
    String consultId = order.getConsultId();
    // TODO ---- 待定 ------> todoByliming
    String orderId = Optional.ofNullable(OrderService.selectByConsultId(consultId))
        .get().getOrderId();
    //发送事件去触发状态机
    return handler.handleEventWithState(getMessage(event, orderId)/*,
        Order.isPresent() ? Order.get().getStatus() : OrderStatus.INIT*/);
  }
}
