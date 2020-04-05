package com.jkys.consult.service.pay;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.jkys.consult.model.CoinOrderModel;
import com.jkys.consult.request.OrderPayRequest;
import com.jkys.phobos.annotation.Service;
import org.springframework.validation.annotation.Validated;

@Service("consult-service.PayInfoRpcService:1.0.0")
@Validated
@JsonRpcService("/pay")
public interface PayInfoRpcService {

  Boolean checkAccountBalance(@JsonRpcParam("orderId") String orderId);

  /**
   * 咨询订单发起支付获取支付串
   * @param consultId
   * @return
   */
  CoinOrderModel payString(@JsonRpcParam("consultId") String consultId, @JsonRpcParam("orderId") int payWay);

  CoinOrderModel payGo(@JsonRpcParam("orderId") OrderPayRequest request);

  /**
   * 查询用户咨询医生的云币信息
   * @param patientId 当前用户
   * @param doctorId 咨询的医生（暂时不用，预留后期不同医生定价）
   */
  CoinOrderModel queryCoin(@JsonRpcParam("patientId") Long patientId, @JsonRpcParam("doctorId") Long doctorId);

  /**
   * 批量查询医生咨询价格
   * @param doctorIds 医生ID
   * @return
   */
//    List<CoinOrderModel> batchQueryAdvisoryPrice(List<Long> doctorIds);
}
