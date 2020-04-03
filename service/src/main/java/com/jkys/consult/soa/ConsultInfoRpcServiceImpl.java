package com.jkys.consult.soa;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.jkys.consult.common.BasePage;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.logic.ConsultStateLogic;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.service.consult.ConsultInfoRpcService;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class ConsultInfoRpcServiceImpl implements ConsultInfoRpcService {

  @Autowired
  ConsultStateLogic consultStateLogic;

  @Autowired
  ConsultLogic consultLogic;

  /**
   * 创建咨询单
   * @param doctorId
   * @param patientId
   * @param consultType
   * @return
   */
  @Override
  public String createConsult(Long doctorId, Long patientId, Integer consultType) {
    String consultId = consultLogic.createConsult(doctorId, patientId, consultType);
    return consultId;
  }

  /**
   * 完成咨询单
   * @param consultId
   * @return
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
   * @param consultId
   * @return
   */
  @Override
  public ConsultInfoModel searchConsultDetail(String consultId) {
    return consultLogic.searchConsultDetail(consultId);
  }

  /**
   * 当前患者，指定咨询类型和咨询单状态下，咨询单列表
   * @param patientId
   * @param consultType
   * @param consultState
   * @return
   */
  @Override
  public BasePage<ConsultInfoModel> searchConsultList(Long patientId, String consultType,
      String consultState) {
    return consultLogic.searchConsultList(patientId, consultType, consultState);
  }

  /**
   * 当前医生和患者咨询单状态
   * @param doctorId
   * @param patientId
   * @return
   */
  @Override
  public String currentConsultState(Long doctorId, Long patientId) {
    return consultLogic.currentConsultState(doctorId, patientId).getStatus();
  }

  /**
   * 校验当前咨询单12小时内是否回复过
   * @param doctorId
   * @param patientId
   * @return
   */
  @Override
  public Boolean checkWhetherResponseIn12hours(Long doctorId, Long patientId) {
    ConsultStatus status = consultLogic.currentConsultState(doctorId, patientId);
    if(ConsultStatus.MAY_CHANGE_DOCTOR.equals(status)){
      return false;
    }
    return true;
  }

}
