package com.jkys.consult.infrastructure.event.comsumer;

import static com.jkys.consult.statemachine.enums.OrderEvents.CREATE;
import static com.jkys.consult.statemachine.enums.OrderEvents.REFUND;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.bean.OrderDomainEvent;
import com.jkys.consult.logic.OrderLogic;
import com.jkys.consult.logic.OrderStateLogic;
import com.jkys.consult.statemachine.enums.OrderEvents;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDomainListener {

//    @Autowired
//    private DemoService demoService;

/*    @Resource(name = "orderPersister")
    private StateMachinePersister<OrderStatus, OrderEvents, Order> orderPersister;

    @Autowired
    private StateMachineFactory<OrderStatus, OrderEvents> orderStatusMachineFactory;*/

  @Autowired
  private EventBus eventBus;

  @PostConstruct
  public void init() {
    eventBus.register(this);
  }

  @Autowired
  OrderStateLogic orderStateLogic;

  @Autowired
  OrderLogic orderLogic;

  @SneakyThrows
  @Subscribe
  public void handleEvent(OrderDomainEvent domainEvent) {
    OrderEvents event = domainEvent.getEvent();
    Order order = domainEvent.getOrder();

    // 需要订单持久化操作
    if (event.equals(CREATE)) {
      orderLogic.createOrder(domainEvent.getOrder());
      // 需要订单退款操作
    } else if (event.equals(REFUND)) {
      orderLogic.refundOrder(domainEvent.getOrder());
    }
    orderStateLogic.handleAction(event, order);

/*
        // invoke application service or domain service
        System.out.println("ConsultListener: createConsult......");

        Order order = event.getOrder();

        StateMachine<OrderStatus, OrderEvents> stateMachine = orderStatusMachineFactory.getStateMachine();
        stateMachine.sendEvent(OrderEvents.CREATE);

        orderPersister.persist(stateMachine, order);

        //恢复
        orderPersister.restore(stateMachine, order);
        //查看恢复后状态机的状态
        System.out.println("恢复后的状态：" + stateMachine.getState().getId());
        */

  }
}
