package com.jkys.consult.statemachine;

import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.statemachine.enums.OrderEvents.CREATE;
import static com.jkys.consult.statemachine.enums.OrderEvents.REFUND;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.bean.OrderDomainEvent;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.event.GuavaDomainEventPublisher;
import com.jkys.consult.logic.OrderLogic;
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
public class OrderActionConfig {

  @Autowired
  GuavaDomainEventPublisher publisher;

  @Autowired
  OrderLogic orderLogic;

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
      // TODO ---- 如何捕获状态机异常 ------> todoByliming
      throw new ServerException(SERVER_ERROR, "状态机异常");
    };
  }

  /**
   * 中止咨询单触发订单退款
   */
  @Bean(name = "orderRefundAction")
  public Action<ConsultStatus, ConsultEvents> orderRefundAction() {

    log.info("中止咨询单触发订单退款 orderRefundAction");

    return context -> {
      // 订单创建相关请求
//      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);
      Consult consult = (Consult) context.getMessageHeader(Constants.CONSULT);

//      Order order = new Order();
//      // TODO ---- 此处由于咨询单和订单ID都用同一个bizCode, 所以直接setOrderId ------> todoByliming
//      order.setOrderId(consult.getConsultId());
//      order.setConsultId(consult.getConsultId());

      OrderDomainEvent event = OrderDomainEvent.builder()
          .consult(consult)
          .event(REFUND)
          .build();

      publisher.publish(event);
    };
  }

  /**
   * 创建咨询单触发创建订单
   */
  @Bean(name = "orderCreateAction"/*, autowire = Autowire.BY_TYPE*/)
  public Action<ConsultStatus, ConsultEvents> orderCreateAction() {

    return context -> {
      // 订单创建相关请求
//      String bizcode = (String) context.getMessageHeader(Constants.BIZ_CODE);
      Consult consult = (Consult) context.getMessageHeader(Constants.CONSULT);

      // 从context中获取状态机
//      StateMachine<ConsultStatus, ConsultEvents> stateMachine = context.getStateMachine();

//      Order order = new Order();
//      // TODO ---- 此处由于咨询单和订单ID都用同一个bizCode, 所以直接setOrderId ------> todoByliming
////      order.setOrderId(consult.getConsultId());
//      order.setConsultId(consult.getConsultId());

      OrderDomainEvent event = OrderDomainEvent.builder()
          .consult(consult)
          .event(CREATE)
          .build();

      publisher.publish(event);

//    MyStateMachineUtils.setCurrentState(stateMachine, ConsultStatus.PROCESSING);

//      bizOrderCreateBizManager.process(createRequest);
    };
  }

  /**
   * 创建咨询单触发创建订单
   */
  @Bean(name = "orderAutoPayAction"/*, autowire = Autowire.BY_TYPE*/)
  public Action<OrderStatus, OrderEvents> orderAutoPayAction() {

    return context -> {
//      Consult consult = (Consult) context.getMessageHeader(Constants.CONSULT);
      Order order = context.getMessageHeaders().get(Constants.ORDER,Order.class);
      // 开药门诊, 自动支付订单
//      if (PRESCRIBE_CONSULT_TYPE.equals(consult.getConsultType())) {
//        OrderDomainEvent event = OrderDomainEvent.builder()
//            .consult(consult)
//            .event(PAY)
//            .build();
//
//        publisher.publish(event);
//      }
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
