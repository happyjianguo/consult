package com.jkys.consult.soa;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.jkys.consult.model.PrescriptionInfoModel;
import com.jkys.consult.service.prescription.PrescriptionInfoRpcService;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class PrescriptionInfoRpcServiceImpl implements PrescriptionInfoRpcService {

  @Override
  public PrescriptionInfoModel searchApplicationformDetail(Long applicationFormId) {
    return null;
  }

  @Override
  public Boolean rejectApplicationform(Long applicationFormId) {
    return null;
  }

  @Override
  public Boolean approveApplicationform(Long applicationFormId) {
    return null;
  }
}
