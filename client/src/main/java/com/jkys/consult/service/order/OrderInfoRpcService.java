package com.jkys.consult.service.order;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.jkys.phobos.annotation.Service;
import org.springframework.validation.annotation.Validated;

@Service("consult-service.OrderInfoRpcService:1.0.0")
@Validated
@JsonRpcService("/order")
public interface OrderInfoRpcService {

  Boolean cancelOrder(@JsonRpcParam("orderId") String orderId);

  Boolean payOrder(@JsonRpcParam("orderId") String orderId);

}
