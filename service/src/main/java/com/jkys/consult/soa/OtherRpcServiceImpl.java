package com.jkys.consult.soa;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.logic.ConsultStateLogic;
import com.jkys.consult.service.other.OtherRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class OtherRpcServiceImpl implements OtherRpcService {

  @Autowired
  ConsultStateLogic consultStateLogic;

  @Autowired
  ConsultLogic consultLogic;

  @Override
  public Boolean terminateConsult(String consultId) {
    boolean result = consultLogic.terminateConsult(consultId);
    return result;
  }
}
