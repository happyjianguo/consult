package com.jkys.consult.referTo;

import com.jkys.consult.referTo.bean.advisory.CoinOrderModel;
import com.jkys.consult.referTo.bean.request.AdvisoryRequest;
import com.jkys.consult.referTo.bean.response.BaseResponse;
import com.jkys.phobos.annotation.Name;
import com.jkys.phobos.annotation.Service;
import java.util.List;

/**
 * 病人--咨询订单化客户端逻辑处理
 * @author yangZh
 * @since  2018-07-13
 */
@Service("jkys-shine.PatientAdvisoryService:1.0.0")
public interface PatientAdvisoryService {

    /**
     * 确认支付动作（生成初始订单，咨询单并扣减糖币）
     * @param request 使用 医生id  病人id
     */
    CoinOrderModel payGo(AdvisoryRequest request);

    /**
     * 更换医生支付
     */
    CoinOrderModel changeDoctor(AdvisoryRequest request);
    /**
     * 咨询订单发起支付获取支付串
     * @param bizCode
     * @return
     */
    CoinOrderModel payString(@Name("bizCode") String bizCode, @Name("payWay") int payWay);

    /**
     * 取消支付
     * @param infoId 咨询单id
     * @return true or false
     */
    BaseResponse cancelOrder(@Name("infoId") Long infoId);

    /**
     * 结束咨询并发送im消息
     *
     * @param patientId 病人id
     *
     * @deprecated use {@link #finishAdvisoryByGroup(AdvisoryRequest)}
     */
    @Deprecated
    BaseResponse updateOrderOver(@Name("patientId") Long patientId, @Name("doctorId") Long doctorId);


    /**
     * 结束咨询并发送im消息
     * 按 im聊天组id结束咨询，同时兼容支持老版本医生通过patientId结束付费咨询
     */
    BaseResponse finishAdvisoryByGroup(AdvisoryRequest request);

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
    List<CoinOrderModel> batchQueryAdvisoryPrice(List<Long> doctorIds);

}
