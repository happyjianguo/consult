package com.jkys.consult.statemachine;

import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
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
 * 咨询单状态机配置
 */
@Configuration
//@EnableStateMachine(name = "ConsultStatusMachine")
@EnableStateMachineFactory(name = "ConsultStatusMachineFactory")
public class ConsultStatusMachineConfig extends
    EnumStateMachineConfigurerAdapter<ConsultStatus, ConsultEvents> {

//  @Resource(name = "consultPersister")
//  private StateMachinePersister<ConsultStatus, ConsultEvents, Consult> consultPersister;

  @Resource(name = "consultErrorHandlerAction")
  private Action<ConsultStatus, ConsultEvents> consultErrorHandlerAction;

  @Resource(name = "sendFinishMsgAction")
  private Action<ConsultStatus, ConsultEvents> sendFinishMsgAction;

  @Resource(name = "sendCheckResponseMessageAction")
  private Action<ConsultStatus, ConsultEvents> sendCheckResponseMessageAction;

  @Resource(name = "orderCreateAction")
  private Action<ConsultStatus, ConsultEvents> orderCreateAction;

  @Resource(name = "orderRefundAction")
  private Action<ConsultStatus, ConsultEvents> orderRefundAction;

  /**
   * 咨询单状态机ID
   */
//  public static final String ConsultStatusMachineId = "ConsultStatusMachineId";

  @Override
  public void configure(
      StateMachineConfigurationConfigurer<ConsultStatus, ConsultEvents> config)
      throws Exception {
    config
        .withConfiguration()
        .machineId(Constants.CONSULT_CONTEXT_CREATE_KEY)
        .autoStartup(true)
        .listener(listener());
  }

  /**
   * 配置状态
   */
  @Override
  public void configure(StateMachineStateConfigurer<ConsultStatus, ConsultEvents> states)
      throws Exception {
    states
        .withStates()
        .initial(ConsultStatus.INIT)
        .states(EnumSet.allOf(ConsultStatus.class));
  }

  /**
   * 配置状态转换事件关系
   */
  @Override
  public void configure(
      StateMachineTransitionConfigurer<ConsultStatus, ConsultEvents> transitions)
      throws Exception {
    transitions
        .withExternal()
        .source(ConsultStatus.INIT).target(ConsultStatus.WAIT_FOR_PROCESS)
        .event(ConsultEvents.CREATE)
        .action(orderCreateAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(ConsultStatus.WAIT_FOR_PROCESS).target(ConsultStatus.PROCESSING)
        .event(ConsultEvents.START)
        // TODO ---- 咨询单开启后发送消息给延迟队列， ------> todoByliming
        .action(sendCheckResponseMessageAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(ConsultStatus.WAIT_FOR_PROCESS).target(ConsultStatus.CANCELED).event(
        ConsultEvents.CANCEL)
        // 前端调用是否超时12小时接口
        // 如果超时，调用推荐列表接口
        .and()
        .withExternal()
        .source(ConsultStatus.PROCESSING).target(ConsultStatus.MAY_CHANGE_DOCTOR)
        .event(ConsultEvents.CHANGE_POSSIBILITY)
        .and()
        .withExternal()
        .source(ConsultStatus.MAY_CHANGE_DOCTOR).target(ConsultStatus.STILL_WAIT)
        .event(ConsultEvents.WAIT)
        .and()
        .withExternal()
        .source(ConsultStatus.MAY_CHANGE_DOCTOR).target(ConsultStatus.TERMINATED)
        .event(ConsultEvents.CHANGE_DOCTOR)
        .and()
        .withExternal()
        .source(ConsultStatus.PROCESSING).target(ConsultStatus.TERMINATED)
        .event(ConsultEvents.TERMINATE)
        .action(orderRefundAction, consultErrorHandlerAction)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(ConsultStatus.MAY_CHANGE_DOCTOR).target(ConsultStatus.TERMINATED)
        .event(ConsultEvents.TERMINATE)
        .action(orderRefundAction, consultErrorHandlerAction)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(ConsultStatus.STILL_WAIT).target(ConsultStatus.TERMINATED)
        .event(ConsultEvents.TERMINATE)
        .action(orderRefundAction, consultErrorHandlerAction)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(ConsultStatus.PROCESSING).target(ConsultStatus.COMPLETED)
        .event(ConsultEvents.COMPLETE)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(ConsultStatus.MAY_CHANGE_DOCTOR).target(ConsultStatus.COMPLETED)
        .event(ConsultEvents.COMPLETE)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(ConsultStatus.STILL_WAIT).target(ConsultStatus.COMPLETED)
        .event(ConsultEvents.COMPLETE)
        .action(sendFinishMsgAction, consultErrorHandlerAction);

//        .and()
//        .withChoice()
//        .source(ConsultStatus.CHECK_POSSIBILITY_CHANGE_DOCTOR_CHOICE)
//        .first(ConsultStatus.MAY_CHANGE_DOCTOR, new ConsultCheckPossibilityChoiceGuard(),
//            new ConsultCheckPossibilityChoiceAction())
//        .last(ConsultStatus.CONSULTING)
//        .and()
//        .withExternal().source(ConsultStatus.MAY_CHANGE_DOCTOR).target(ConsultStatus.CHECK_WHETHER_CHANGE_DOCTOR_CHOICE)
//        .event(ConsultEvents.CHANGE_DOCTOR)
//        .and()
//        .withChoice()
//        .source(ConsultStatus.CHECK_WHETHER_CHANGE_DOCTOR_CHOICE)
//        .first(ConsultStatus.TERMINATED, new ConsultCheckWhetherChoiceGuard())
//        .last(ConsultStatus.CONSULTING)
//        .and()
//        .withExternal().source(ConsultStatus.CONSULTING).target(ConsultStatus.TERMINATED).event(
//        ConsultEvents.TERMINATE);
  }

//  @Bean
//  public Action<States, Events> errorAction() {
//    return new Action<States, Events>() {
//      @Override
//      public void execute(StateContext<States, Events> context) {
//        // RuntimeException("MyError") added to context
//        Exception exception = context.getException();
//        exception.getMessage();
//      }
//    };
//  }


  @Bean(name = "consultListener")
  public StateMachineListener<ConsultStatus, ConsultEvents> listener() {
    return new StateMachineListenerAdapter<ConsultStatus, ConsultEvents>() {

      @Override
      public void stateEntered(State<ConsultStatus, ConsultEvents> state) {

      }

      @Override
      public void transition(Transition<ConsultStatus, ConsultEvents> transition) {
        System.out.println("move from:{" + ofNullableState(transition.getSource()) + "} " +
            "to:{" + ofNullableState(transition.getTarget()) + "}");
      }

      @Override
      public void stateChanged(State<ConsultStatus, ConsultEvents> from,
          State<ConsultStatus, ConsultEvents> to) {

        if (null != from) {
          System.out.println("State change from " + from.getId() + " to " + to.getId());
        } else {
          System.out.println("State change to " + to.getId());
        }
      }

      @Override
      public void eventNotAccepted(Message<ConsultEvents> event) {
        System.err.println("event not accepted: {" + event + "}");
      }

      private Object ofNullableState(State s) {
        return Optional.ofNullable(s)
            .map(State::getId)
            .orElse(null);
      }
    };
  }

}