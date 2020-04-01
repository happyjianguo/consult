package com.jkys.consult.logic;

public interface ConsultLogic {

  String createConsult(Long doctorId, Long patientId, Integer consultType);

  Boolean terminateConsult(String consultId);

  Boolean completeConsult(String consultId);

//  Boolean startConsult(String consultId);

//  Boolean changeDoctor(String consultId);

//  Boolean wait(String consultId);

}
