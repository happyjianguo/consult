package com.jkys.consult.soa;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.logic.ConsultStateLogic;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.reponse.ConsultInfoResponse;
import com.jkys.consult.request.ConsultInfoRequest;
import com.jkys.consult.request.PageRequest;
import com.jkys.consult.service.consult.ConsultInfoRpcService;
import com.jkys.consult.service.order.OrderInfoRpcService;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import com.jkys.phobos.ApiGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AutoJsonRpcServiceImpl
public class ConsultInfoRpcServiceImpl implements ConsultInfoRpcService {

  @Autowired
  private ConsultStateLogic consultStateLogic;

  @Autowired
  private ConsultLogic consultLogic;

  @Autowired
  private OrderInfoRpcService orderInfoRpcService;

  /**
   * 创建咨询单
   */
  @Override
  public ConsultInfoResponse createConsult(ConsultInfoRequest request) {
    Long patientId = ObjectUtils.isEmpty(request.getPatientId()) ? ApiGateway.getUserId()
        : request.getPatientId();
    Long doctorId = request.getDoctorId();
    Integer consultType = request.getConsultType();
    String mallOrderId = request.getMallOrderId();

    String consultId = consultLogic.createConsult(doctorId, patientId, consultType, mallOrderId);

    return ConsultInfoResponse.builder()
        .doctorId(doctorId)
        .patientId(patientId)
        .consultType(consultType)
        .consultId(consultId)
        .build();
  }

  /**
   * 创建咨询单
   * @param doctorId
   * @param patientId
   * @param consultType
   * @return
   */
//  @Override
//  public Boolean createConsult(Long doctorId, Long patientId, Integer consultType) {
//
//    String consultId = consultLogic.createConsult(doctorId, patientId, consultType);
//    return StringUtils.isNotEmpty(consultId);
//  }

  /**
   * 完成咨询单
   */
  @Override
  public Boolean completeConsult(String consultId) {
    return consultLogic.completeConsult(consultId);
  }

//  @Override
//  public Boolean changeDoctor(String consultId) {
//    return consultLogic.changeDoctor(consultId);
//  }

  /**
   * 咨询单详情
   */
  @Override
  public ConsultInfoModel searchConsultDetail(String consultId) {
    return consultLogic.searchConsultDetail(consultId);
  }

  /**
   * 当前患者，指定咨询类型和咨询单状态下，咨询单列表
   */
  @Override
  public BasePage<ConsultInfoModel> searchConsultList(PageRequest<ConsultInfoRequest> pageRequest) {
    return consultLogic.searchConsultList(pageRequest);
  }

  /**
   * 当前医生和患者咨询单状态
   */
  @Override
  public ConsultInfoResponse currentConsultModelState(Long doctorId, Long patientId) {
    patientId = ObjectUtils.isEmpty(patientId) ? ApiGateway.getUserId() : patientId;
    return consultLogic.currentConsultModelState(doctorId, patientId);
  }

  /**
   * 当前医生和患者咨询单状态
   */
  @Override
  public ConsultInfoResponse currentConsultModelState(Long doctorId, Long patientId,
      Integer consultType) {
    patientId = ObjectUtils.isEmpty(patientId) ? ApiGateway.getUserId() : patientId;
    return consultLogic.currentConsultModelState(doctorId, patientId, consultType);
  }

  /**
   * 校验当前咨询单12小时内是否回复过
   */
  @Override
  public Boolean checkWhetherResponseIn12hours(Long doctorId, Long patientId) {
    patientId = ObjectUtils.isEmpty(patientId) ? ApiGateway.getUserId() : patientId;
    ConsultStatus status = consultLogic.unprocessedExpertConsult(doctorId, patientId).getStatus();
    if (ConsultStatus.MAY_CHANGE_DOCTOR.equals(status)) {
      return false;
    }
    return true;
  }

}
