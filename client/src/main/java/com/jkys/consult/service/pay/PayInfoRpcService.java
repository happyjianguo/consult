package com.jkys.consult.service.pay;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.jkys.phobos.annotation.Service;
import org.springframework.validation.annotation.Validated;

@Service("consult-service.PayInfoRpcService:1.0.0")
@Validated
@JsonRpcService("/pay")
public interface PayInfoRpcService {

  Boolean checkAccountBalance(@JsonRpcParam("orderId") String orderId);

  String getPaySring(@JsonRpcParam("consultId") String consultId);
}
