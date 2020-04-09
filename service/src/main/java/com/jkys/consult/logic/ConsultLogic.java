package com.jkys.consult.logic;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.reponse.ConsultInfoResponse;
import com.jkys.consult.request.ConsultInfoRequest;
import com.jkys.consult.request.PageRequest;

public interface ConsultLogic {

  String createConsult(Long doctorId, Long patientId, Integer consultType);

  String createConsult(Long doctorId, Long patientId, Integer consultType, String mallOrderId);

  Boolean terminateConsult(String consultId);

  Boolean completeConsult(String consultId);

  ConsultInfoModel searchConsultDetail(String consultId);

  BasePage<ConsultInfoModel> searchConsultList(PageRequest<ConsultInfoRequest> pageRequest);

  Consult currentConsultState(Long doctorId, Long patientId);

  Consult currentConsultState(Long doctorId, Long patientId, Integer consultType);

  ConsultInfoResponse currentConsultModelState(Long doctorId, Long patientId);

  ConsultInfoResponse currentConsultModelState(Long doctorId, Long patientId, Integer consultType);

  Consult unprocessedPrescribeConsult(Long patientId, Integer consultType);

  Consult unprocessedExpertConsult(Long doctorId, Long patientId);

  boolean triggerTerminateEvent(Long doctorId, Long patient);

  boolean triggerCancelEvent(Long doctorId, Long patient);

  boolean triggerMayChangeDoctorEvent(Long doctorId, Long patient);

//  Boolean startConsult(String consultId);

//  Boolean changeDoctor(String consultId);

//  Boolean wait(String consultId);

}
