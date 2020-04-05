package com.jkys.consult.logic;

import com.jkys.consult.model.CoinOrderModel;
import com.jkys.consult.request.OrderPayRequest;

public interface PayInfoLogic {

  CoinOrderModel payGo(OrderPayRequest request);

  CoinOrderModel queryCoin(Long userId, Long doctorId);

  CoinOrderModel payString(String bizCode, int payWay);
}
