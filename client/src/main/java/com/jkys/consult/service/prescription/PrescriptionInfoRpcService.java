package com.jkys.consult.service.prescription;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.jkys.consult.model.PrescriptionInfoModel;
import com.jkys.phobos.annotation.Service;
import org.springframework.validation.annotation.Validated;

@Service("consult-service.PrescriptionInfoRpcService:1.0.0")
@Validated
@JsonRpcService("/pay")
public interface PrescriptionInfoRpcService {

  PrescriptionInfoModel searchApplicationformDetail(@JsonRpcParam("applicationFormId") Long applicationFormId);

  Boolean rejectApplicationform(@JsonRpcParam("applicationFormId") Long applicationFormId);

  Boolean approveApplicationform(@JsonRpcParam("applicationFormId") Long applicationFormId);
}
