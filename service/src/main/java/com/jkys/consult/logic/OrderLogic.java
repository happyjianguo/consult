package com.jkys.consult.logic;

import com.jkys.consult.common.bean.Order;

public interface OrderLogic {

  Boolean createOrder(Order order);

  Boolean createOrder(String consultId);

  Boolean cancelOrder(Order order);

  Boolean refundOrder(Order order);

  Boolean payOrder(Order order);

}
