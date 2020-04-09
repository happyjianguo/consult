package com.jkys.consult.statemachine;

import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.statemachine.enums.ConsultEvents.CANCEL;
import static com.jkys.consult.statemachine.enums.ConsultEvents.CHANGE_DOCTOR;
import static com.jkys.consult.statemachine.enums.ConsultEvents.CHANGE_POSSIBILITY;
import static com.jkys.consult.statemachine.enums.ConsultEvents.COMPLETE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.CREATE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.START;
import static com.jkys.consult.statemachine.enums.ConsultEvents.TERMINATE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.WAIT;
import static com.jkys.consult.statemachine.enums.ConsultStatus.CANCELED;
import static com.jkys.consult.statemachine.enums.ConsultStatus.COMPLETED;
import static com.jkys.consult.statemachine.enums.ConsultStatus.INIT;
import static com.jkys.consult.statemachine.enums.ConsultStatus.MAY_CHANGE_DOCTOR;
import static com.jkys.consult.statemachine.enums.ConsultStatus.PROCESSING;
import static com.jkys.consult.statemachine.enums.ConsultStatus.STILL_WAIT;
import static com.jkys.consult.statemachine.enums.ConsultStatus.TERMINATED;
import static com.jkys.consult.statemachine.enums.ConsultStatus.WAIT_FOR_PROCESS;

import com.jkys.consult.exception.ServerException;
import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
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
 * 咨询单状态机配置
 */
@Configuration
//@EnableStateMachine(name = "ConsultStatusMachine")
@EnableStateMachineFactory(name = "ConsultStatusMachineFactory")
@Slf4j
public class ConsultStatusMachineConfig extends
    EnumStateMachineConfigurerAdapter<ConsultStatus, ConsultEvents> {

//  @Resource(name = "consultPersister")
//  private StateMachinePersister<ConsultStatus, ConsultEvents, Consult> consultPersister;

  @Resource(name = "consultErrorHandlerAction")
  private Action<ConsultStatus, ConsultEvents> consultErrorHandlerAction;

  @Resource(name = "sendFinishMsgAction")
  private Action<ConsultStatus, ConsultEvents> sendFinishMsgAction;

  @Resource(name = "sendStartMsgAction")
  private Action<ConsultStatus, ConsultEvents> sendStartMsgAction;

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
        .initial(INIT)
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
        .source(INIT).target(WAIT_FOR_PROCESS)
        .event(CREATE)
        .action(orderCreateAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(WAIT_FOR_PROCESS).target(PROCESSING)
        .event(START)
        .action(sendStartMsgAction, consultErrorHandlerAction)
        // TODO ---- 咨询单开启后发送消息给延迟队列， ------> todoByliming
//        .action(sendCheckResponseMessageAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(WAIT_FOR_PROCESS).target(CANCELED).event(
        CANCEL)
        // 前端调用是否超时12小时接口
        // 如果超时，调用推荐列表接口
        .and()
        .withExternal()
        .source(PROCESSING).target(MAY_CHANGE_DOCTOR)
        .event(CHANGE_POSSIBILITY)
        .and()
        .withExternal()
        .source(MAY_CHANGE_DOCTOR).target(STILL_WAIT)
        .event(WAIT)
        .and()
        .withExternal()
        .source(MAY_CHANGE_DOCTOR).target(TERMINATED)
        .event(CHANGE_DOCTOR)
        .and()
        .withExternal()
        .source(PROCESSING).target(TERMINATED)
        .event(TERMINATE)
        .action(orderRefundAction, consultErrorHandlerAction)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(MAY_CHANGE_DOCTOR).target(TERMINATED)
        .event(TERMINATE)
        .action(orderRefundAction, consultErrorHandlerAction)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(STILL_WAIT).target(TERMINATED)
        .event(TERMINATE)
        .action(orderRefundAction, consultErrorHandlerAction)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(PROCESSING).target(COMPLETED)
        .event(COMPLETE)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(MAY_CHANGE_DOCTOR).target(COMPLETED)
        .event(COMPLETE)
        .action(sendFinishMsgAction, consultErrorHandlerAction)
        .and()
        .withExternal()
        .source(STILL_WAIT).target(COMPLETED)
        .event(COMPLETE)
        .action(sendFinishMsgAction, consultErrorHandlerAction);

//        .and()
//        .withChoice()
//        .source(CHECK_POSSIBILITY_CHANGE_DOCTOR_CHOICE)
//        .first(MAY_CHANGE_DOCTOR, new ConsultCheckPossibilityChoiceGuard(),
//            new ConsultCheckPossibilityChoiceAction())
//        .last(CONSULTING)
//        .and()
//        .withExternal().source(MAY_CHANGE_DOCTOR).target(CHECK_WHETHER_CHANGE_DOCTOR_CHOICE)
//        .event(CHANGE_DOCTOR)
//        .and()
//        .withChoice()
//        .source(CHECK_WHETHER_CHANGE_DOCTOR_CHOICE)
//        .first(TERMINATED, new ConsultCheckWhetherChoiceGuard())
//        .last(CONSULTING)
//        .and()
//        .withExternal().source(CONSULTING).target(TERMINATED).event(
//        TERMINATE);
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
      public void stateMachineError(StateMachine<ConsultStatus, ConsultEvents> stateMachine, Exception exception) {
        log.error("consultListener stateMachine execute error = ", exception);
        throw new ServerException(SERVER_ERROR, "状态机异常");
      }

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