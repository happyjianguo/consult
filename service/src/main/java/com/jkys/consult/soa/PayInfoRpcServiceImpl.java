package com.jkys.consult.soa;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.jkys.consult.logic.PayInfoLogic;
import com.jkys.consult.service.pay.PayInfoRpcService;
import com.jkys.consult.reponse.PayOrderResponse;
import com.jkys.consult.request.OrderPayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// TODO ---- 待实现 ------> todoByliming
@Service
@AutoJsonRpcServiceImpl
public class PayInfoRpcServiceImpl implements PayInfoRpcService {

  @Autowired
  PayInfoLogic payInfoLogic;

  @Override
  public Boolean checkAccountBalance(String orderId) {
    return null;
  }

  @Override
  public PayOrderResponse payString(String consultId, int payWay) {
    return null;
  }

  @Override
  public PayOrderResponse payGo(OrderPayRequest request) {
    return payInfoLogic.payGo(request);
  }

  @Override
  public PayOrderResponse queryCoin(Long patientId, Long doctorId) {
    return null;
  }
}
