package com.jkys.consult.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.request.ConsultInfoRequest;
import com.jkys.consult.request.PageRequest;
import java.util.List;

public interface ConsultService extends IService<Consult> {

  Consult selectByConsultId(String consultId);

  void updateByConsultId(Consult consult);

  int saveConsult();

  BasePage<Consult> pagePatientConsultByTypeAndState(
      PageRequest<ConsultInfoRequest> pageRequest);

  Consult selectByDoctorListAndPatient(List<Long> doctorIds, Long patientId);

  Consult selectByDoctorAndPatient(Long doctorId, Long patientId);

  Consult selectByDoctorAndPatientAndConsultType(Long doctorId, Long patientId, Integer consultType);

  Consult selectByPatientAndConsultType(Long patientId, Integer consultType);

//  BasePage<User> mySelectPage(@Param("pg") BasePage<User> myPage);

}