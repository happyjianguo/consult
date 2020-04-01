package com.jkys.consult.service.doctor;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.jkys.consult.model.DoctorInfoModel;
import com.jkys.phobos.annotation.Service;
import java.util.List;
import org.springframework.validation.annotation.Validated;

@Service("consult-service.DoctorInfoRpcService:1.0.0")
@Validated
@JsonRpcService("/doctor")
public interface DoctorInfoRpcService {

  List<DoctorInfoModel> getRecommendedDoctorList();
}
