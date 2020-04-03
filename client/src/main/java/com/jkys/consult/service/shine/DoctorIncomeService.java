package com.jkys.consult.service.shine;

import com.jkys.consult.shine.request.DoctorIncomeRequest;
import com.jkys.consult.shine.request.ModifyDoctorAdvisoryPriceRequest;
import com.jkys.consult.shine.response.EmptyResponse;
import com.jkys.consult.shine.response.QueryDoctorAdvisoryPriceResponse;
import com.jkys.consult.shine.response.SimpleResponse;
import com.jkys.phobos.annotation.Service;

/**
 * 医生收入服务类
 *
 * @author xiecw
 * @date 2018/09/04
 **/
@Service("jkys-shine.DoctorIncomeService:1.0.0")
public interface DoctorIncomeService {

    /**
     * 查询医生收入列表-分页查询
     */
    EmptyResponse queryDoctorIncome(DoctorIncomeRequest request);

    /**
     * 查询医生咨询定价
     */
    QueryDoctorAdvisoryPriceResponse queryDoctorAdvisoryPrice();

    /**
     * 修改医生的咨询定价
     */
    SimpleResponse modifyDoctorAdvisoryPrice(ModifyDoctorAdvisoryPriceRequest request);
}