package com.jkys.consult.logic;

import com.jkys.consult.reponse.PayOrderResponse;
import com.jkys.consult.request.OrderPayRequest;

public interface PayInfoLogic {

  PayOrderResponse payGo(OrderPayRequest request);

  PayOrderResponse queryCoin(Long userId, Long doctorId);

  PayOrderResponse payString(String bizCode, int payWay);
}
