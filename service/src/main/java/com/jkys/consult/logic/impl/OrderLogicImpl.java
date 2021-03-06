package com.jkys.consult.logic.impl;

import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.common.component.ExceptionMessage.COIN_FAIL;
import static com.jkys.consult.statemachine.enums.OrderEvents.CANCEL;
import static com.jkys.consult.statemachine.enums.OrderEvents.PAY;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.db.mybatisplus.component.CustomSequenceGenerator;
import com.jkys.consult.infrastructure.rpc.coincenter.CoinCenterRemoteRpcService;
import com.jkys.consult.logic.DoctorIncomeLogic;
import com.jkys.consult.logic.OrderLogic;
import com.jkys.consult.logic.OrderStateLogic;
import com.jkys.consult.logic.PayInfoLogic;
import com.jkys.consult.reponse.PayOrderResponse;
import com.jkys.consult.request.OrderPayRequest;
import com.jkys.consult.service.ConsultService;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.statemachine.enums.OrderEvents;
import com.jkys.consult.statemachine.enums.OrderStatus;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Service
@Slf4j
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

  @Resource
  private CoinCenterRemoteRpcService coinCenterRemoteRpcService;

  @Autowired
  private CustomSequenceGenerator sequenceGenerator;

  // TODO ---- FAKE ------> todoByliming
  @Autowired
  DoctorIncomeLogic doctorIncomeLogic;

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
    } catch (Exception e) {
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
      Order order = orderService.selectByConsultId(consultId);
      // TODO ---- 调用云币中心退款 ------> todoByliming
      if (!coinCenterRemoteRpcService.isIncreaseCoin(order)) {
        throw new ServerException(SERVER_ERROR, COIN_FAIL);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    return true;
  }

  @Override
  public PayOrderResponse payOrder(OrderPayRequest request) {
    PayOrderResponse response;
    try {
      // TODO ---- 是否异步，待定 ------> todoByliming
      response = payInfoLogic.payGo(request);
      if (response.getCoinPay() == 1) {
        orderStateLogic.handleAction(PAY,
            Order.builder()
                .orderId(request.getOrderId())
                .build());
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    return response;
  }

  @Override
  public Boolean createOrder(String consultId) {
//    StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder
//        .build(beanFactory);

    try {
      // TODO ---- 获取医生价格 ------> todoByliming
      Consult consult = consultService.selectByConsultId(consultId);
      Long doctorId = consult.getDoctorId();
      int price = doctorIncomeLogic.getDoctorPrice(doctorId);

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
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }

    return true;
  }

}
