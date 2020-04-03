package com.jkys.consult.service.shine;

import com.jkys.consult.shine.bean.CoinOrderModel;
import com.jkys.consult.shine.request.AdvisoryRequest;
import com.jkys.phobos.annotation.Name;
import com.jkys.phobos.annotation.Service;

/**
 * 病人--咨询订单化客户端逻辑处理
 * @author yangZh
 * @since  2018-07-13
 */
@Service("jkys-shine.PatientAdvisoryService:1.0.0")
public interface PatientAdvisoryService {

    /**
     * 咨询订单发起支付获取支付串
     * @param bizCode
     * @return
     */
    CoinOrderModel payString(@Name("bizCode") String bizCode, @Name("payWay") int payWay);

    CoinOrderModel payGo(AdvisoryRequest request);

    /**
     * 查询用户咨询医生的云币信息
     * @param userId 当前用户
     * @param doctorId 咨询的医生（暂时不用，预留后期不同医生定价）
     */
    CoinOrderModel queryCoin(@Name("userId") Long userId, @Name("doctorId") Long doctorId);

    /**
     * 批量查询医生咨询价格
     * @param doctorIds 医生ID
     * @return
     */
//    List<CoinOrderModel> batchQueryAdvisoryPrice(List<Long> doctorIds);

}
