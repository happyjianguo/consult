package com.jkys.consult.statemachine;

import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.enums.OrderStatus;
import java.util.EnumSet;
import java.util.Optional;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
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
        .initial(OrderStatus.INIT)
        .states(EnumSet.allOf(OrderStatus.class));
  }

  /**
   * 配置状态转换事件关系
   */
  @Override
  public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvents> transitions)
      throws Exception {
    transitions
        .withExternal().source(OrderStatus.INIT).target(OrderStatus.WAIT_FOR_PAY)
        .event(OrderEvents.CREATE)
        .and()
        .withExternal().source(OrderStatus.WAIT_FOR_PAY).target(OrderStatus.PAYED)
        .event(OrderEvents.PAY)
        .action(consultStartAction, orderErrorHandlerAction)
        .and()
        .withExternal().source(OrderStatus.WAIT_FOR_PAY).target(OrderStatus.CANCELED)
        .event(OrderEvents.CANCEL)
        .action(consultCancelAction, orderErrorHandlerAction)
        .and()
        .withExternal().source(OrderStatus.PAYED).target(OrderStatus.REFUNDED)
        .event(OrderEvents.REFUND);
  }

  @Bean(name = "orderStateListener")
  public StateMachineListener<OrderStatus, OrderEvents> listener() {
    return new StateMachineListenerAdapter<OrderStatus, OrderEvents>() {
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