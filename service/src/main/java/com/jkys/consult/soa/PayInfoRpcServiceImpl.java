package com.jkys.consult.soa;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.jkys.consult.logic.PayInfoLogic;
import com.jkys.consult.service.pay.PayInfoRpcService;
import com.jkys.consult.model.CoinOrderModel;
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
  public CoinOrderModel payString(String consultId, int payWay) {
    return null;
  }

  @Override
  public CoinOrderModel payGo(OrderPayRequest request) {
    return null;
  }

  @Override
  public CoinOrderModel queryCoin(Long patientId, Long doctorId) {
    return null;
  }
}
