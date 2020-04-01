package com.jkys.consult.statemachine.persist;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.jkys.consult.common.bean.ConsultStateMachineContext;
import com.jkys.consult.common.bean.ConsultStateMachineContextIsNull;
import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.infrastructure.db.mapper.ConsultStateMachineContextMapper;
import com.jkys.consult.service.ConsultStateMachineContextService;
import com.jkys.consult.service.ConsultService;
import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.MessageHeaders;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.kryo.MessageHeadersSerializer;
import org.springframework.statemachine.kryo.StateMachineContextSerializer;
import org.springframework.statemachine.kryo.UUIDSerializer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("consultStateMachineContextPersist")
public class ConsultStateMachineContextPersist implements StateMachinePersist<ConsultStatus, ConsultEvents, Consult> {

  @Resource
  private RedisStateMachineContextRepository<ConsultStatus, ConsultEvents> redisStateMachineContextRepository;

  @Autowired
  private ConsultStateMachineContextMapper contextMapper;

  @Autowired
  private ConsultStateMachineContextService contextService;

  @Autowired
  ConsultService consultService;

  //  加入存储到DB的数据repository, biz_order_state_machine_context表结构：
  //  bizOrderId
  //  contextStr
  //  curStatus
  //  updateTime


  @Autowired
  ConsultStateMachineContextPersist consultStateMachineContextPersist;

  /**
   * Write a {@link StateMachineContext} into a persistent store
   * with a context object {@code T}.
   *
   * @param context    the context
   * @param contextObj the context ojb
   * @throws Exception the exception
   */
  @Override
  @Transactional
  public void write(StateMachineContext<ConsultStatus, ConsultEvents> context, Consult contextObj) throws Exception {

    String currentStatus = context.getState().getStatus();
    String serialize = serialize(context);
    String consultId = contextObj.getConsultId();

    // TODO ---- redis有问题 ------> todoByliming
//    redisStateMachineContextRepository.save(context, consultId);
    //  save to db
    ConsultStateMachineContext queryResult = contextService.selectByBizCode(consultId);

    if (null == queryResult) {
      ConsultStateMachineContext consultContext = new ConsultStateMachineContext(consultId,
          currentStatus, serialize);
      contextService.insertConsultContext(consultContext);
    } else {
      queryResult.setCurrentStatus(currentStatus);
      queryResult.setContextStr(serialize);
      contextService.updateByBizCode(queryResult);
    }
  }

  /**
   * Read a {@link StateMachineContext} from a persistent store
   * with a context object {@code T}.
   *
   * @param contextObj the context ojb
   * @return the state machine context
   * @throws Exception the exception
   */
  @Override
  public StateMachineContext<ConsultStatus, ConsultEvents> read(Consult contextObj) throws Exception {

    String consultId = contextObj.getConsultId();

    // TODO ---- redis ------> todoByliming
//    StateMachineContext<ConsultStatus, ConsultEvents> context = redisStateMachineContextRepository.getContext(consultId);
    StateMachineContext<ConsultStatus, ConsultEvents> context = null;
    //redis 访缓存击穿
    if (null != context && Constants.STATE_MACHINE_CONTEXT_ISNULL.equalsIgnoreCase(context.getId())) {
      return null;
    }
    //redis 为空走db
    if (null == context) {
      ConsultStateMachineContext queryResult = contextService.selectByBizCode(consultId);
      // TODO ---- redis ------> todoByliming
      if (null != queryResult) {
        context = deserialize(queryResult.getContextStr());
        // TODO ---- redis ------> todoByliming
//        redisStateMachineContextRepository.save(context, consultId);
      } else {
        context = new ConsultStateMachineContextIsNull();
        // TODO ---- redis ------> todoByliming
//        redisStateMachineContextRepository.save(context, consultId);
      }
    }
    return context;
  }

  private String serialize(StateMachineContext<ConsultStatus, ConsultEvents> context) throws UnsupportedEncodingException {
    Kryo kryo = kryoThreadLocal.get();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Output output = new Output(out);
    kryo.writeObject(output, context);
    output.close();
    return Base64.getEncoder().encodeToString(out.toByteArray());
  }

  @SuppressWarnings("unchecked")
  private StateMachineContext<ConsultStatus, ConsultEvents> deserialize(String data) throws UnsupportedEncodingException {
    if (StringUtils.isEmpty(data)) {
      return null;
    }
    Kryo kryo = kryoThreadLocal.get();
    ByteArrayInputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(data));
    Input input = new Input(in);
    return kryo.readObject(input, StateMachineContext.class);
  }

  private static final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>() {

    @SuppressWarnings("rawtypes")
    @Override
    protected Kryo initialValue() {
      Kryo kryo = new Kryo();
      kryo.addDefaultSerializer(StateMachineContext.class, new StateMachineContextSerializer());
      kryo.addDefaultSerializer(MessageHeaders.class, new MessageHeadersSerializer());
      kryo.addDefaultSerializer(UUID.class, new UUIDSerializer());
      return kryo;
    }
  };

//  @Bean(name = "consultPersister")
//  public StateMachinePersister<ConsultStatus, ConsultEvents, Consult> ConsultStateMachineContextPersister() {
//    return new DefaultStateMachinePersister<>(consultStateMachineContextPersist);
//  }

//  @Autowired
//  ConsultService ConsultService;

  /**
   * 持久化配置 实际使用中，可以配合redis等，进行持久化操作
   */
  @Bean(name = "consultPersister")
  public StateMachinePersister<ConsultStatus, ConsultEvents, Consult> persister() {
    return new DefaultStateMachinePersister<>(
        new StateMachinePersist<ConsultStatus, ConsultEvents, Consult>() {
          @Override
          public void write(StateMachineContext<ConsultStatus, ConsultEvents> context,
              Consult consult) {
            //此处并没有进行持久化操作
            consult.setStatus(context.getState());
            consultService.update(consult,new UpdateWrapper<Consult>().lambda().eq(Consult::getConsultId, consult.getConsultId()));
          }

          @Override
          public StateMachineContext<ConsultStatus, ConsultEvents> read(Consult consult) {
            Consult result = consultService.selectByConsultId(consult.getConsultId());
            //此处直接获取order中的状态，其实并没有进行持久化读取操作
            StateMachineContext<ConsultStatus, ConsultEvents> context = new DefaultStateMachineContext<>(
                result.getStatus(), null, null, null, null,
                Constants.CONSULT_CONTEXT_CREATE_KEY);
            return context;
          }
        });
  }

}