package com.jkys.consult.logic;

import com.jkys.consult.common.BasePage;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.statemachine.enums.ConsultStatus;

public interface ConsultLogic {

  String createConsult(Long doctorId, Long patientId, Integer consultType);

  Boolean terminateConsult(String consultId);

  Boolean completeConsult(String consultId);

  ConsultInfoModel searchConsultDetail(String consultId);

  BasePage<ConsultInfoModel> searchConsultList(Long patientId, String consultType, String consultState);

  ConsultStatus currentConsultState(Long doctorId, Long patientId);

//  Boolean startConsult(String consultId);

//  Boolean changeDoctor(String consultId);

//  Boolean wait(String consultId);

}
