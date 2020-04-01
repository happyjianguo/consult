package com.jkys.consult.soa;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.logic.OrderLogic;
import com.jkys.consult.logic.OrderStateLogic;
import com.jkys.consult.service.order.OrderInfoRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class OrderInfoRpcServiceImpl implements OrderInfoRpcService {

  @Autowired
  OrderStateLogic orderStateLogic;

  @Autowired
  OrderLogic orderLogic;

  @Override
  public Boolean cancelOrder(String orderId) {
    boolean result = orderLogic.cancelOrder(new Order().setOrderId(orderId));
    return result;
  }

  @Override
  public Boolean payOrder(String orderId) {
    boolean result = orderLogic.payOrder(new Order().setOrderId(orderId));
    return result;
  }

}
