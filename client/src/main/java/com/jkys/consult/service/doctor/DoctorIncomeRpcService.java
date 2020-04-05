package com.jkys.consult.service.doctor;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.jkys.consult.reponse.DoctorConsultPriceResponse;
import com.jkys.consult.reponse.DoctorIncomeResponse;
import com.jkys.consult.request.DoctorPriceRequest;
import com.jkys.consult.request.PageRequest;
import com.jkys.phobos.annotation.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 医生收入服务类
 **/
@Service("consult-service.DoctorIncomeRpcService:1.0.0")
@Validated
@JsonRpcService("/doctor")
public interface DoctorIncomeRpcService {

    /**
     * 查询医生收入列表-分页查询
     */
//    DoctorIncomeResponse searchDoctorIncome(DoctorIncomeRequest request);
    DoctorIncomeResponse searchDoctorIncome(@JsonRpcParam("pageRequest") PageRequest pageRequest);

    /**
     * 查询医生咨询定价
     */
    DoctorConsultPriceResponse getDoctorPrice(@JsonRpcParam("doctorId") Long doctorId);

    /**
     * 查询医生咨询定价
     */
//    Integer getDoctorPrice(Long doctorId);

    /**
     * 修改医生的咨询定价
     */
    Boolean updateDoctorPrice(@JsonRpcParam("request") DoctorPriceRequest request);
}