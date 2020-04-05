package com.jkys.consult.logic.impl;

import static com.jkys.consult.common.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.statemachine.enums.OrderEvents.CANCEL;
import static com.jkys.consult.statemachine.enums.OrderEvents.PAY;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.db.mybatisplus.component.CustomSequenceGenerator;
import com.jkys.consult.infrastructure.rpc.usercenter.DoctorRemoteRpcService;
import com.jkys.consult.logic.OrderLogic;
import com.jkys.consult.logic.OrderStateLogic;
import com.jkys.consult.logic.PayInfoLogic;
import com.jkys.consult.service.ConsultService;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.request.OrderPayRequest;
import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.enums.OrderStatus;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Service
public class OrderLogicImpl implements OrderLogic {

  @Resource(name = "orderPersister")
  private StateMachinePersister<OrderStatus, OrderEvents, Order> orderPersister;
//  @Autowired
//  private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister;

  @Autowired
  OrderStateLogic orderStateLogic;

  @Autowired
  OrderService orderService;

  @Autowired
  ConsultService consultService;

  @Autowired
  PayInfoLogic payInfoLogic;

  @Autowired
  private CustomSequenceGenerator sequenceGenerator;

  // TODO ---- FAKE ------> todoByliming
  @Autowired
  DoctorRemoteRpcService doctorRemoteRpcService;

  @Override
  public Boolean createOrder(Order order) {
    return createOrder(order.getConsultId());
  }

  @Override
  public Boolean createOrder(Consult consult) {
    return createOrder(consult.getConsultId());
  }

  @Override
  public Boolean cancelOrder(Order order) {
    try {
      orderStateLogic.handleAction(CANCEL, order);
    }catch (Exception e){
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    return true;
  }

  @Override
  public Boolean refundOrder(Order order) {
    return refundOrder(order.getConsultId());
  }

  @Override
  public Boolean refundOrder(Consult consult) {
    return refundOrder(consult.getConsultId());
  }

  @Override
  public Boolean refundOrder(String consultId) {
    try {
      // TODO ---- 调用云币中心退款 ------> todoByliming


    }catch (Exception e){
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    return true;
  }

  @Override
  public Boolean payOrder(OrderPayRequest request) {
    Order order = orderService.selectByOrderId(request.getOrderId());
    try {
      // TODO ---- 调用云币中心付款 ------> todoByliming
      //  下面操作需要同步，并且捕捉异常
      payInfoLogic.payGo(request);
      orderStateLogic.handleAction(PAY, order);
    }catch (Exception e){
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    return true;
  }

  @Override
  public Boolean createOrder(String consultId) {
//    StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder
//        .build(beanFactory);

    try {
      // TODO ---- 获取医生价格 ------> todoByliming
      Consult consult = consultService.selectByConsultId(consultId);
      Long doctorId = consult.getDoctorId();
      int price = doctorRemoteRpcService.getDoctorPrice(doctorId);

      // TODO ---- 是否要生成orderID ------> todoByliming
//      final String orderId = sequenceGenerator.genBizCode();
      String orderId = consultId;

      Order order = Order.builder()
          .consultId(consultId)
          .orderId(orderId)
          // TODO ---- order是否包含医生和患者，待定 ------> todoByliming
          .doctorId(consult.getDoctorId())
          .patientId(consult.getPatientId())
          .price(price)
          .build();

      orderService.save(order);

//        .status(OrderStatus.INIT).build();
//    stateMachine.getState().getId()

//    Message<OrderEvents> eventMsg = getMessage(CREATE, orderId);

//    boolean sendResult = stateMachine.sendEvent(eventMsg);

//      orderStateLogic.handleAction(CREATE, order);
    }catch (Exception e){
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }

    return true;
  }

}
