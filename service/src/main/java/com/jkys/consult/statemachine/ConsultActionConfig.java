package com.jkys.consult.statemachine;

import static com.jkys.consult.statemachine.enums.ConsultEvents.CANCEL;
import static com.jkys.consult.statemachine.enums.ConsultEvents.START;
import static com.jkys.consult.statemachine.enums.OrderEvents.CREATE;
import static com.jkys.consult.statemachine.enums.OrderEvents.REFUND;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.ConsultDomainEvent;
import com.jkys.consult.common.bean.GeneralEvent;
import com.jkys.consult.common.bean.GeneralEventType;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.bean.OrderDomainEvent;
import com.jkys.consult.infrastructure.event.GuavaDomainEventPublisher;
import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.enums.OrderStatus;
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
  @Bean(name = "consultErrorHandlerAction")
  public Action<ConsultStatus, ConsultEvents> consultErrorHandlerAction() {

    return context -> {
      RuntimeException exception = (RuntimeException) context.getException();
      log.error("stateMachine execute error = ", exception);
      context.getStateMachine()
          .getExtendedState().getVariables()
          .put(RuntimeException.class, exception);
    };
  }

  /**
   * 异常处理Action
   *
   * @return action对象
   */
  @Bean(name = "orderErrorHandlerAction")
  public Action<OrderStatus, OrderEvents> orderErrorHandlerAction() {

    return context -> {
      RuntimeException exception = (RuntimeException) context.getException();
      log.error("stateMachine execute error = ", exception);
      context.getStateMachine()
          .getExtendedState().getVariables()
          .put(RuntimeException.class, exception);
    };
  }

  /**
   * 咨询单结束触发 发送IM消息
   * @return
   */
  @Bean(name = "sendFinishMsgAction")
  public Action<ConsultStatus, ConsultEvents> sendFinishMsgAction(){

    log.info("咨询单结束触发 发送IM消息sendFinishMsgAction");

    return context -> {
      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);

      Consult consult = new Consult();
      consult.setConsultId(bizcode);

      // TODO ---- 根据完结条件不同发送不同的消息 ------> todoByliming
      // TODO 开启咨询单时发送消息谁来做, 如果问诊，需要添加action

      ConsultStatus source = context.getSource().getId();
      ConsultStatus target = context.getTarget().getId();

      String FINISH_TEXT = "test...";
      // Constants.FINISH_TEXT

      GeneralEvent event = GeneralEvent.builder()
          .object(consult)
          .message(FINISH_TEXT)
          .event(GeneralEventType.SEND)
          .build();

      publisher.publish(event);
    };
  }

  /**
   * 订单支付触发咨询单开启
   * @return
   */
  @Bean(name = "consultStartAction")
  public Action<OrderStatus, OrderEvents> consultRefundAction(){

    return context -> {
      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);

      Consult consult = new Consult();
      // TODO ---- 此处由于咨询单和订单ID都用同一个bizCode, 所以直接setConsultId ------> todoByliming
      consult.setConsultId(bizcode);

      ConsultDomainEvent event = ConsultDomainEvent.builder()
          .consult(consult)
          .event(START)
          .build();

      publisher.publish(event);
    };
  }

  /**
   * 订单取消触发咨询单取消
   * @return
   */
  @Bean(name = "consultCancelAction")
  public Action<OrderStatus, OrderEvents> consultCancelAction(){

    return context -> {
      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);

      Consult consult = new Consult();
      // TODO ---- 此处由于咨询单和订单ID都用同一个bizCode, 所以直接setConsultId ------> todoByliming
      consult.setConsultId(bizcode);

      ConsultDomainEvent event = ConsultDomainEvent.builder()
          .consult(consult)
          .event(CANCEL)
          .build();

      publisher.publish(event);
    };
  }

  /**
   * 中止咨询单触发订单退款
   * @return
   */
  @Bean(name = "orderRefundAction")
  public Action<ConsultStatus, ConsultEvents> orderRefundAction(){

    log.info("中止咨询单触发订单退款 orderRefundAction");

    return context -> {
      // 订单创建相关请求
      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);

      Order order = new Order();
      // TODO ---- 此处由于咨询单和订单ID都用同一个bizCode, 所以直接setOrderId ------> todoByliming
      order.setOrderId(bizcode);

      OrderDomainEvent event = OrderDomainEvent.builder()
          .order(order)
          .event(REFUND)
          .build();

      publisher.publish(event);
    };
  }

  /**
   * 创建咨询单触发创建订单
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
      // TODO ---- 此处由于咨询单和订单ID都用同一个bizCode, 所以直接setOrderId ------> todoByliming
      order.setOrderId(bizcode);

      OrderDomainEvent event = OrderDomainEvent.builder()
          .order(order)
          .event(CREATE)
          .build();

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
