package com.jkys.consult.service.consult;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.jkys.consult.common.BasePage;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.phobos.annotation.Service;
import org.springframework.validation.annotation.Validated;

@Service("consult-service.ConsultInfoRpcService:1.0.0")
@Validated
@JsonRpcService("/consult")
public interface ConsultInfoRpcService {

  //  @JsonRpcMethod("ConsultInfoRpcService.createConsult")
  String createConsult(@JsonRpcParam("doctorId") Long doctorId,
      @JsonRpcParam("patientId") Long patientId, @JsonRpcParam("consultType") Integer consultType);

//  Boolean changeDoctor(String consultId);

  ConsultInfoModel searchConsultDetail(@JsonRpcParam("consultId") String consultId);

  BasePage<ConsultInfoModel> searchConsultList(@JsonRpcParam("patientId") Long patientId,
      @JsonRpcParam("consultType") String consultType,
      @JsonRpcParam("consultState") String consultState);

  String currentConsultState(@JsonRpcParam("doctorId") Long doctorId,
      @JsonRpcParam("patientId") Long patientId);

  Boolean checkWhetherResponseIn12hours(@JsonRpcParam("consultId") String consultId);

  Boolean completeConsult(@JsonRpcParam("consultId") String consultId);
}
