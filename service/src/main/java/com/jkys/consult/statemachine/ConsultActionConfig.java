package com.jkys.consult.statemachine;

import static com.jkys.consult.statemachine.enums.ConsultEvents.CANCEL;
import static com.jkys.consult.statemachine.enums.ConsultEvents.START;
import static com.jkys.consult.statemachine.enums.OrderEvents.CREATE;
import static com.jkys.consult.statemachine.enums.OrderEvents.REFUND;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.ConsultDomainEvent;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.bean.OrderDomainEvent;
import com.jkys.consult.infrastructure.event.GuavaDomainEventPublisher;
import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;

@Configuration
@Slf4j
public class ConsultActionConfig {

  @Autowired
  GuavaDomainEventPublisher publisher;

  /**
   * 异常处理Action
   *
   * @return action对象
   */
  @Bean(name = "errorHandlerAction")
  public Action<ConsultStatus, ConsultEvents> errorHandlerAction() {

    return context -> {
      RuntimeException exception = (RuntimeException) context.getException();
      log.error("stateMachine execute error = ", exception);
      context.getStateMachine()
          .getExtendedState().getVariables()
          .put(RuntimeException.class, exception);
    };
  }

  /**
   * 订单支付触发咨询单开启
   * @return
   */
  @Bean(name = "consultStartAction")
  public Action<ConsultStatus, ConsultEvents> consultRefundAction(){

    return context -> {
      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);

      Consult consult = new Consult();
      consult.setConsultId(bizcode);

      ConsultDomainEvent event = ConsultDomainEvent.builder()
          .consult(consult)
          .event(START)
          .build();
      event.setConsult(consult);

      publisher.publish(event);
    };
  }

  /**
   * 订单支付触发咨询单开启
   * @return
   */
  @Bean(name = "consultCancelAction")
  public Action<ConsultStatus, ConsultEvents> consultCancelAction(){

    return context -> {
      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);

      Consult consult = new Consult();
      consult.setConsultId(bizcode);

      ConsultDomainEvent event = ConsultDomainEvent.builder()
          .consult(consult)
          .event(CANCEL)
          .build();
      event.setConsult(consult);

      publisher.publish(event);
    };
  }

  /**
   * 订单退款
   * @return
   */
  @Bean(name = "orderRefundAction")
  public Action<ConsultStatus, ConsultEvents> orderRefundAction(){

    return context -> {
      // 订单创建相关请求
      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);

      Order order = new Order();
      order.setConsultId(bizcode);

      OrderDomainEvent event = OrderDomainEvent.builder()
          .order(order)
          .event(REFUND)
          .build();
      event.setOrder(order);

      publisher.publish(event);
    };
  }

  /**
   * 创建订单
   * @return
   */
  @Bean(name = "orderCreateAction"/*, autowire = Autowire.BY_TYPE*/)
  public Action<ConsultStatus, ConsultEvents> orderCreateAction(){

    return context -> {
      // 订单创建相关请求
      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);

      // 从context中获取状态机
//      StateMachine<ConsultStatus, ConsultEvents> stateMachine = context.getStateMachine();

      Order order = new Order();
      order.setConsultId(bizcode);

      OrderDomainEvent event = OrderDomainEvent.builder()
          .order(order)
          .event(CREATE)
          .build();
      event.setOrder(order);

      publisher.publish(event);
//    MyStateMachineUtils.setCurrentState(stateMachine, ConsultStatus.PROCESSING);

//      bizOrderCreateBizManager.process(createRequest);
    };
  }

  /**
   * 自动跳转到close的Action
   * <p>
   * 比如超时未处理，希望关单，可以使用此action
   *
   * @return action对象
   */
//  @Bean(name = "toCloseAction",autowire = Autowire.BY_TYPE)
//  public Action<BizOrderStatusEnum, BizOrderStatusChangeEventEnum> toCloseAction() {
//    return context -> {
//      StateMachine<BizOrderStatusEnum, BizOrderStatusChangeEventEnum> stateMachine = context.getStateMachine();
//
//      BizOrderStatusRequest statusRequest = (BizOrderStatusRequest) context.getMessageHeader(BizOrderConstants.BIZORDER_CONTEXT_KEY);
//
//      log.info("order info={},stateMachine id={},uuid={}, jump from {} to toClose status",
//          statusRequest,
//          stateMachine.getId(),
//          stateMachine.getUuid(),
//          stateMachine.getState().getId());
//
//      bizOrderToCloseBizManager.process(statusRequest);
//
//    };
//  }
}
