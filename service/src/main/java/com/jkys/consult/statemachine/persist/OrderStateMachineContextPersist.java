package com.jkys.consult.statemachine.persist;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.bean.OrderStateMachineContext;
import com.jkys.consult.common.bean.OrderStateMachineContextIsNull;
import com.jkys.consult.infrastructure.db.mapper.OrderStateMachineContextMapper;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.service.OrderStateMachineContextService;
import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.enums.OrderStatus;
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

@Component("orderStateMachineContextPersist")
public class OrderStateMachineContextPersist implements StateMachinePersist<OrderStatus, OrderEvents, Order> {

  @Resource
  private RedisStateMachineContextRepository<OrderStatus, OrderEvents> redisStateMachineContextRepository;

  @Autowired
  private OrderStateMachineContextMapper contextMapper;

  @Autowired
  private OrderStateMachineContextService contextService;

  @Autowired
  OrderService orderService;

  //  加入存储到DB的数据repository, biz_order_state_machine_context表结构：
  //  bizOrderId
  //  contextStr
  //  curStatus
  //  updateTime


  @Autowired
  OrderStateMachineContextPersist orderStateMachineContextPersist;

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
  public void write(StateMachineContext<OrderStatus, OrderEvents> context, Order contextObj) throws Exception {

    String currentStatus = context.getState().getStatus();
    String serialize = serialize(context);
    String consultId = contextObj.getConsultId();

    // TODO ---- redis有问题 ------> todoByliming
//    redisStateMachineContextRepository.save(context, orderId);
    //  save to db
    OrderStateMachineContext queryResult = contextService.selectByBizCode(consultId);

    if (null == queryResult) {
      OrderStateMachineContext orderContext = new OrderStateMachineContext(consultId,
          currentStatus, serialize);
      contextService.insertOrderContext(orderContext);
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
  public StateMachineContext<OrderStatus, OrderEvents> read(Order contextObj) throws Exception {

    String orderId = contextObj.getOrderId();

    // TODO ---- redis ------> todoByliming
//    StateMachineContext<OrderStatus, OrderEvents> context = redisStateMachineContextRepository.getContext(orderId);
    StateMachineContext<OrderStatus, OrderEvents> context = null;
    //redis 访缓存击穿
    if (null != context && Constants.STATE_MACHINE_CONTEXT_ISNULL.equalsIgnoreCase(context.getId())) {
      return null;
    }
    //redis 为空走db
    if (null == context) {
      OrderStateMachineContext queryResult = contextService.selectByBizCode(orderId);
      // TODO ---- redis ------> todoByliming
      if (null != queryResult) {
        context = deserialize(queryResult.getContextStr());
        // TODO ---- redis ------> todoByliming
//        redisStateMachineContextRepository.save(context, orderId);
      } else {
        context = new OrderStateMachineContextIsNull();
        // TODO ---- redis ------> todoByliming
//        redisStateMachineContextRepository.save(context, orderId);
      }
    }
    return context;
  }

  private String serialize(StateMachineContext<OrderStatus, OrderEvents> context) throws UnsupportedEncodingException {
    Kryo kryo = kryoThreadLocal.get();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Output output = new Output(out);
    kryo.writeObject(output, context);
    output.close();
    return Base64.getEncoder().encodeToString(out.toByteArray());
  }

  @SuppressWarnings("unchecked")
  private StateMachineContext<OrderStatus, OrderEvents> deserialize(String data) throws UnsupportedEncodingException {
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

//  @Bean(name = "orderPersister")
//  public StateMachinePersister<OrderStatus, OrderEvents, Order> OrderStateMachineContextPersister() {
//    return new DefaultStateMachinePersister<>(orderStateMachineContextPersist);
//  }

  /**
   * 持久化配置 实际使用中，可以配合redis等，进行持久化操作
   */
  @Bean(name = "orderPersister")
  public StateMachinePersister<OrderStatus, OrderEvents, Order> persister() {
    return new DefaultStateMachinePersister<>(
        new StateMachinePersist<OrderStatus, OrderEvents, Order>() {
          @Override
          public void write(StateMachineContext<OrderStatus, OrderEvents> context,
              Order order) {
            //此处并没有进行持久化操作
            order.setStatus(context.getState());
            orderService.update(order, new UpdateWrapper<Order>().lambda().eq(Order::getOrderId, order.getOrderId()));
          }

          @Override
          public StateMachineContext<OrderStatus, OrderEvents> read(Order order) {
            Order result = orderService.selectByOrderId(order.getOrderId());
            //此处直接获取order中的状态，其实并没有进行持久化读取操作
            StateMachineContext<OrderStatus, OrderEvents> context = new DefaultStateMachineContext<>(
                result.getStatus(), null, null, null, null,
                Constants.ORDER_CONTEXT_CREATE_KEY);
            return context;
          }
        });
  }

}