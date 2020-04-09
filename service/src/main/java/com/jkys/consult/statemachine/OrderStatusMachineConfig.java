package com.jkys.consult.statemachine;

import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.statemachine.enums.OrderEvents.CANCEL;
import static com.jkys.consult.statemachine.enums.OrderEvents.CREATE;
import static com.jkys.consult.statemachine.enums.OrderEvents.PAY;
import static com.jkys.consult.statemachine.enums.OrderEvents.REFUND;
import static com.jkys.consult.statemachine.enums.OrderStatus.CANCELED;
import static com.jkys.consult.statemachine.enums.OrderStatus.INIT;
import static com.jkys.consult.statemachine.enums.OrderStatus.PAYED;
import static com.jkys.consult.statemachine.enums.OrderStatus.REFUNDED;
import static com.jkys.consult.statemachine.enums.OrderStatus.WAIT_FOR_PAY;

import com.jkys.consult.exception.ServerException;
import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.enums.OrderStatus;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

/**
 * 订单状态机配置
 */
@Configuration
//@EnableStateMachine(name = "orderStateMachine")
@EnableStateMachineFactory(name = "orderStatusMachineFactory")
@Slf4j
public class OrderStatusMachineConfig extends
    EnumStateMachineConfigurerAdapter<OrderStatus, OrderEvents> {

  /**
   * 订单状态机ID
   */
//  public static final String orderStatusMachineId = "orderStatusMachineId";

  @Resource(name = "orderErrorHandlerAction")
  private Action<OrderStatus, OrderEvents> orderErrorHandlerAction;

  @Resource(name = "consultStartAction")
  private Action<OrderStatus, OrderEvents> consultStartAction;

  @Resource(name = "orderAutoPayAction")
  private Action<OrderStatus, OrderEvents> orderAutoPayAction;

  @Resource(name = "consultCancelAction")
  private Action<OrderStatus, OrderEvents> consultCancelAction;

  @Override
  public void configure(StateMachineConfigurationConfigurer<OrderStatus, OrderEvents> config)
      throws Exception {
    config
        .withConfiguration()
        .machineId(Constants.ORDER_CONTEXT_CREATE_KEY)
        .autoStartup(true)
        .listener(listener());
  }

  /**
   * 配置状态
   */
  @Override
  public void configure(StateMachineStateConfigurer<OrderStatus, OrderEvents> states)
      throws Exception {
    states
        .withStates()
        .initial(INIT)
//        .choice(PAYING)
        .states(EnumSet.allOf(OrderStatus.class));
  }

  /**
   * 配置状态转换事件关系
   */
  @Override
  public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvents> transitions)
      throws Exception {
    transitions
        .withExternal().source(INIT).target(WAIT_FOR_PAY)
        .event(CREATE)
        .action(orderAutoPayAction, orderErrorHandlerAction)
//        .and()
//        .withExternal().source(WAIT_FOR_PAY).target(PAYING)
//        .event(PAY)
//        .and()
//        .withChoice()
//        .source(PAYING)
//        .first(PAYED, new PayChoiceGuard(), consultStartAction, orderErrorHandlerAction)
//        .last(WAIT_FOR_PAY)
        .and()
        .withExternal().source(WAIT_FOR_PAY).target(PAYED)
        .event(PAY)
        .action(consultStartAction, orderErrorHandlerAction)
        .and()
        .withExternal().source(WAIT_FOR_PAY).target(CANCELED)
        .event(CANCEL)
        .action(consultCancelAction, orderErrorHandlerAction)
        .and()
        .withExternal().source(PAYED).target(REFUNDED)
        .event(REFUND);
  }

  @Bean(name = "orderStateListener")
  public StateMachineListener<OrderStatus, OrderEvents> listener() {
    return new StateMachineListenerAdapter<OrderStatus, OrderEvents>() {

      @Override
      public void stateMachineError(StateMachine<OrderStatus, OrderEvents> stateMachine, Exception exception) {
        log.error("orderStateListener stateMachine execute error = ", exception);
        throw new ServerException(SERVER_ERROR, "状态机异常");
      }

      @Override
      public void transition(Transition<OrderStatus, OrderEvents> transition) {
        System.out.println("order move from:{" + ofNullableState(transition.getSource()) + "} " +
            "to:{" + ofNullableState(transition.getTarget()) + "}");
      }

      @Override
      public void stateChanged(State<OrderStatus, OrderEvents> from,
          State<OrderStatus, OrderEvents> to) {

        if (null != from) {
          System.out.println("order State change from " + from.getId() + " to " + to.getId());
        } else {
          System.out.println("order State change to " + to.getId());
        }
      }

      @Override
      public void eventNotAccepted(Message<OrderEvents> event) {
        System.err.println("order event not accepted: {" + event + "}");
      }

      private Object ofNullableState(State s) {
        return Optional.ofNullable(s)
            .map(State::getId)
            .orElse(null);
      }
    };
  }

}