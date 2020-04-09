package com.jkys.consult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.common.enums.ConsultModelStatus;
import com.jkys.consult.infrastructure.db.mapper.ConsultMapper;
import com.jkys.consult.request.ConsultInfoRequest;
import com.jkys.consult.request.PageRequest;
import com.jkys.consult.service.ConsultService;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import com.jkys.phobos.ApiGateway;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ConsultServiceImpl extends
    ServiceImpl<ConsultMapper, Consult> implements
    ConsultService {

  @Override
  public Consult selectByConsultId(String consultId) {
    Consult context = this.getOne(
        new QueryWrapper<Consult>().lambda().eq(Consult::getConsultId, consultId));
    return context;
  }

  @Override
  public void updateByConsultId(Consult consult) {
    consult.insertOrUpdate();
  }

  @Deprecated
  @Override
  public int saveConsult() {
    return this.baseMapper.insert(new Consult());
  }

  @Override
  public BasePage<Consult> pagePatientConsultByTypeAndState(
      PageRequest<ConsultInfoRequest> pageRequest) {

    ConsultInfoRequest request = pageRequest.getRequest();

    Long patientId = ObjectUtils.isEmpty(request.getPatientId()) ? ApiGateway.getUserId()
        : request.getPatientId();

    ConsultModelStatus modelStatus = ConsultModelStatus.getByStatus(request.getConsultState());
    List<ConsultStatus> consultStatusList = ConsultStatus.getByType(modelStatus);

    BasePage<Consult> consultPage = pageRequest.getPage();
    consultPage = this
        .page(consultPage, new QueryWrapper<Consult>()
            .lambda()
            .nested(i -> i.eq(Consult::getPatientId, patientId)
                .eq(Consult::getConsultType, request.getConsultType())
                .in(Consult::getStatus, consultStatusList)));
    return consultPage;
  }

  @Override
  public Consult selectByDoctorListAndPatient(List<Long> doctorIds, Long patientId) {
    List<Consult> list = this.list(new QueryWrapper<Consult>()
        .lambda()
        .nested(i -> i.in(Consult::getDoctorId, doctorIds)
            .eq(Consult::getPatientId, patientId)));
    return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
  }

  @Override
  public Consult selectByDoctorAndPatient(Long doctorId, Long patientId) {
    Consult consult = this.getOne(new QueryWrapper<Consult>()
        .lambda()
        .nested(i -> i.eq(Consult::getDoctorId, doctorId)
            .eq(Consult::getPatientId, patientId)));
    return consult;
  }

//  @Override
//  public Integer selectByPatientAndConsultType(Long patientId, Integer consultType) {
//    int count = this.count(new QueryWrapper<Consult>()
//        .lambda()
//        .nested(i -> i.eq(Consult::getPatientId, patientId)
//            .eq(Consult::getConsultType, consultType)));
//    return count;
//  }

  @Override
  public Consult selectByDoctorAndPatientAndConsultType(Long doctorId, Long patientId,
      Integer consultType) {
    List<Consult> list = this.list(new QueryWrapper<Consult>()
        .lambda()
        .nested(i -> i.eq(Consult::getDoctorId, doctorId)
            .eq(Consult::getPatientId, patientId)
            .eq(Consult::getConsultType, consultType)));
    return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
  }

  @Override
  public Consult selectByPatientAndConsultType(Long patientId, Integer consultType) {
    List<Consult> list = this.list(new QueryWrapper<Consult>()
        .lambda()
        .nested(i -> i.eq(Consult::getPatientId, patientId)
            .eq(Consult::getConsultType, consultType)));
    return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
  }

}
