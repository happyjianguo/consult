package com.jkys.consult.logic;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.reponse.PayOrderResponse;
import com.jkys.consult.request.OrderPayRequest;

public interface OrderLogic {

  Boolean createOrder(Order order);

  Boolean createOrder(String consultId);

  Boolean createOrder(Consult consult);

  Boolean cancelOrder(Order order);

  Boolean refundOrder(Order order);

  Boolean refundOrder(Consult consult);

  Boolean refundOrder(String consultId);

  PayOrderResponse payOrder(OrderPayRequest request);

}
