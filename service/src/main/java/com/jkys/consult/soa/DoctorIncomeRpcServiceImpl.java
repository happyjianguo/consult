package com.jkys.consult.soa;

import com.jkys.consult.logic.DoctorIncomeLogic;
import com.jkys.consult.reponse.DoctorConsultPriceResponse;
import com.jkys.consult.request.DoctorPriceRequest;
import com.jkys.consult.request.PageRequest;
import com.jkys.consult.service.doctor.DoctorIncomeRpcService;
import com.jkys.consult.reponse.DoctorIncomeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoctorIncomeRpcServiceImpl implements DoctorIncomeRpcService {

    @Autowired
    private DoctorIncomeLogic doctorIncomeLogic;

//    @Override
//    public DoctorIncomeResponse searchDoctorIncome(DoctorIncomeRequest request) {
//        return doctorIncomeLogic.queryDoctorIncome(request);
//    }

    @Override
    public DoctorIncomeResponse searchDoctorIncome(PageRequest pageRequest) {
        return doctorIncomeLogic.queryDoctorIncome(pageRequest);
    }

    @Override
    public DoctorConsultPriceResponse getDoctorPrice(Long doctorId) {
        return doctorIncomeLogic.getDoctorPrice(doctorId);
    }

//    @Override
//    public Integer getDoctorPrice(Long doctorId) {
//        return doctorIncomeLogic.getDoctorPrice(doctorId);
//    }

    @Override
    public Boolean updateDoctorPrice(DoctorPriceRequest request) {
        return doctorIncomeLogic.updateDoctorPrice(request);
    }

}
