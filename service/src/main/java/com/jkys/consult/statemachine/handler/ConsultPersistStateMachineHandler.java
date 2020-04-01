package com.jkys.consult.statemachine.handler;

import static com.jkys.consult.common.CodeMsg.SERVER_ERROR;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import javax.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.LifecycleObjectSupport;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsultPersistStateMachineHandler extends LifecycleObjectSupport {

  private final PersistingStateChangeInterceptor interceptor = new PersistingStateChangeInterceptor();
//  private final CompositePersistStateChangeListener listeners = new CompositePersistStateChangeListener();

  @Resource(name = "consultPersister")
  private StateMachinePersister<ConsultStatus, ConsultEvents, Consult> consultPersister;

  @Autowired
  private StateMachineFactory<ConsultStatus, ConsultEvents> consultStatusMachineFactory;

  /**
   * 实例化一个新的持久化状态机Handler
   *
   */
  public ConsultPersistStateMachineHandler() {
//    this.stateMachine = stateMachine;
  }

  @Override
  protected void onInit() throws Exception {

  }

  /**
   * 处理entity的事件
   *
   * @return 如果事件被接受处理，返回true
   */
  public boolean handleEventWithState(Message<ConsultEvents> event/*, ConsultStatus state*/) {
    StateMachine<ConsultStatus, ConsultEvents> stateMachine = consultStatusMachineFactory.getStateMachine();
    stateMachine.getStateMachineAccessor()
        .doWithAllRegions(function -> function.addStateMachineInterceptor(interceptor));

    // 方式1：
//    stateMachine.stop();
//    List<StateMachineAccess<ConsultStatus, ConsultEvents>> withAllRegions = stateMachine
//        .getStateMachineAccessor()
//        .withAllRegions();
//    for (StateMachineAccess<ConsultStatus, ConsultEvents> a : withAllRegions) {
//      a.resetStateMachine(new DefaultStateMachineContext<>(state, null, null, null));
//    }
//    stateMachine.start();

    // 方式2：
    //恢复
    String consultId = event.getHeaders().get(Constants.BIZ_CODE).toString();
    Consult consult = new Consult();
    consult.setConsultId(consultId);

    try {
      consultPersister.restore(stateMachine, consult);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    //查看恢复后状态机的状态
    log.info("恢复后的状态：" + stateMachine.getState().getId());

    return stateMachine.sendEvent(event);
  }

  private class PersistingStateChangeInterceptor extends
      StateMachineInterceptorAdapter<ConsultStatus, ConsultEvents> {

    // 状态预处理的拦截器方法
    @SneakyThrows
    @Override
    public void postStateChange(State<ConsultStatus, ConsultEvents> state, Message<ConsultEvents> message,
        Transition<ConsultStatus, ConsultEvents> transition, StateMachine<ConsultStatus,
        ConsultEvents> stateMachine) {

      String bizcode = message.getHeaders().get(Constants.BIZ_CODE).toString();
      log.info("当前咨询单ID: " + bizcode);

      Consult consult = Consult
          .builder()
          .consultId(bizcode).build();

      consultPersister.persist(stateMachine, consult);

//      listeners.onPersist(state, message, transition, stateMachine);
    }
  }

  /**
   * 添加listener
   *
   * @param listener the listener
   */
//  public void addPersistStateChangeListener(PersistStateChangeListener listener) {
//    listeners.register(listener);
//  }


  /**
   * 可以通过 addPersistStateChangeListener，增加当前Handler的PersistStateChangeListener。
   * 在状态变化的持久化触发时，会调用相应的实现了PersistStateChangeListener的Listener实例。
   */
//  public interface PersistStateChangeListener {
//
//    /**
//     * 当状态被持久化，调用此方法
//     *
//     * @param stateMachine 状态机实例
//     */
//    void onPersist(State<ConsultStatus, ConsultEvents> state, Message<ConsultEvents> message,
//        Transition<ConsultStatus,
//            ConsultEvents> transition,
//        StateMachine<ConsultStatus, ConsultEvents> stateMachine);
//  }
//
//  private class CompositePersistStateChangeListener extends
//      AbstractCompositeListener<PersistStateChangeListener> implements
//      PersistStateChangeListener {
//
//    @Override
//    public void onPersist(State<ConsultStatus, ConsultEvents> state, Message<ConsultEvents> message,
//        Transition<ConsultStatus, ConsultEvents> transition, StateMachine<ConsultStatus,
//        ConsultEvents> stateMachine) {
//      for (Iterator<PersistStateChangeListener> iterator = getListeners().reverse();
//          iterator.hasNext(); ) {
//        PersistStateChangeListener listener = iterator.next();
//        listener.onPersist(state, message, transition, stateMachine);
//      }
//    }
//  }

}