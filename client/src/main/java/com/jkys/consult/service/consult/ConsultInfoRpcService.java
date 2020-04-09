package com.jkys.consult.service.consult;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.reponse.ConsultInfoResponse;
import com.jkys.consult.request.ConsultInfoRequest;
import com.jkys.consult.request.PageRequest;
import com.jkys.phobos.annotation.Service;
import org.springframework.validation.annotation.Validated;

@Service("consult-service.ConsultInfoRpcService:1.0.0")
@Validated
@JsonRpcService("/consult")
public interface ConsultInfoRpcService {

  //  @JsonRpcMethod("ConsultInfoRpcService.createConsult")
  ConsultInfoResponse createConsult(@JsonRpcParam("request") ConsultInfoRequest request);

//  Boolean changeDoctor(String consultId);

  ConsultInfoModel searchConsultDetail(@JsonRpcParam("consultId") String consultId);

  BasePage<ConsultInfoModel> searchConsultList(@JsonRpcParam("pageRequest") PageRequest<ConsultInfoRequest> pageRequest);

  ConsultInfoResponse currentConsultModelState(@JsonRpcParam("doctorId") Long doctorId,
      @JsonRpcParam("patientId") Long patientId);

  Boolean completeConsult(@JsonRpcParam("consultId") String consultId);

  ConsultInfoResponse currentConsultModelState(Long doctorId, Long patientId, Integer consultType);

  Boolean checkWhetherResponseIn12hours(@JsonRpcParam("doctorId") Long doctorId, @JsonRpcParam("patientId") Long patientId);
}
