package com.jkys.consult.soa;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.jkys.consult.common.BasePage;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.logic.ConsultStateLogic;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.service.consult.ConsultInfoRpcService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class ConsultInfoRpcServiceImpl implements ConsultInfoRpcService {

  @Autowired
  ConsultStateLogic consultStateLogic;

  @Autowired
  ConsultLogic consultLogic;

  @Override
  public Boolean createConsult(Long doctorId, Long patientId, Integer consultType) {
    String consultId = consultLogic.createConsult(doctorId, patientId, consultType);
    return Optional.ofNullable(consultId).isPresent();
  }

  @Override
  public Boolean completeConsult(String consultId) {
    return consultLogic.completeConsult(consultId);
  }

//  @Override
//  public Boolean changeDoctor(String consultId) {
//    return consultLogic.changeDoctor(consultId);
//  }

  @Override
  public ConsultInfoModel searchConsultDetail(String consultId) {
    return null;
  }

  @Override
  public BasePage<ConsultInfoModel> searchConsultList(Long patientId, String consultType,
      String consultState) {
    return null;
  }

  @Override
  public ConsultInfoModel currentConsultState(Long doctorId, Long patientId) {
    return null;
  }

  @Override
  public Boolean checkWhetherResponseIn12hours(String consultId) {
    return null;
  }

}
