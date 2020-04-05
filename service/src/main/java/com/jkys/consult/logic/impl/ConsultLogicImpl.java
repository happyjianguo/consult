package com.jkys.consult.logic.impl;

import static com.jkys.consult.common.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.statemachine.enums.ConsultEvents.COMPLETE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.CREATE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.TERMINATE;
import static java.util.stream.Collectors.toList;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jkys.consult.common.BasePage;
import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.enums.ConsultModelStatus;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.db.mybatisplus.component.CustomSequenceGenerator;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.logic.ConsultStateLogic;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.service.ConsultService;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

@Service
public class ConsultLogicImpl implements ConsultLogic {

  @Resource(name = "consultPersister")
  private StateMachinePersister<ConsultStatus, ConsultEvents, Consult> consultPersister;
//  @Autowired
//  private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister;

  @Autowired
  ConsultStateLogic consultStateLogic;

  @Autowired
  ConsultService consultService;

  @Autowired
  OrderService orderService;

  @Autowired
  private CustomSequenceGenerator sequenceGenerator;

  /**
   * 创建咨询单
   * @param doctorId
   * @param patientId
   * @param consultType
   * @return
   */
  @Override
  public String createConsult(Long doctorId, Long patientId, Integer consultType) {
    return createConsult(doctorId, patientId, consultType, null);
  }

  /**
   * 创建咨询单
   * @param doctorId
   * @param patientId
   * @param consultType
   * @return
   */
  @Override
  public String createConsult(Long doctorId, Long patientId, Integer consultType, String mallOrderId) {
//    StateMachine<OrderStates, OrderEvents> stateMachine = orderStateMachineBuilder
//        .build(beanFactory);

    String consultId;
    try {
      consultId = sequenceGenerator.genBizCode();

      Consult consult = Consult.builder()
          .consultId(consultId)
          .doctorId(doctorId)
          .patientId(patientId)
          .consultType(consultType)
          .mallOrderId(mallOrderId)
          .build();

      consultService.save(consult);

//        .status(ConsultStatus.INIT).build();
//    stateMachine.getState().getId()

//    Message<ConsultEvents> eventMsg = getMessage(CREATE, consultId);

//    boolean sendResult = stateMachine.sendEvent(eventMsg);
      consultStateLogic.handleAction(CREATE, consult);
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }

    return consultId;
  }

  /**
   * 中止咨询单
   * @param consultId
   * @return
   */
  @Override
  public Boolean terminateConsult(String consultId) {
    try {
      // TODO ---- 触发订单退款操作 ------> todoByliming
      consultStateLogic.handleAction(TERMINATE, new Consult().setConsultId(consultId));
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    return true;
  }

  /**
   * 完成咨询单
   * @param consultId
   * @return
   */
  @Override
  public Boolean completeConsult(String consultId) {
    try {
      consultStateLogic.handleAction(COMPLETE, new Consult().setConsultId(consultId));
    } catch (Exception e) {
      e.printStackTrace();
      throw new ServerException(SERVER_ERROR, e.getMessage());
    }
    return true;
  }

  /**
   * 咨询订单详情
   * @param consultId
   * @return
   */
  @Override
  public ConsultInfoModel searchConsultDetail(String consultId) {
    Consult consult = consultService.selectByConsultId(consultId);
    Order order = orderService.selectByConsultId(consultId);
    return getConsultInfoModel(consult, order);
  }

  private ConsultInfoModel getConsultInfoModel(Consult consult, Order order) {
    ConsultInfoModel consultInfoModel = new ConsultInfoModel();
    BeanUtils.copyProperties(order, consultInfoModel);
    BeanUtils.copyProperties(consult, consultInfoModel);

    String status = consult.getStatus().getType().getName();
    consultInfoModel.setStatus(status);

    return consultInfoModel;
  }

  /**
   * 咨询单列表 分页
   * @param patientId
   * @param consultType
   * @param consultState
   * @return
   */
  @Override
  public BasePage<ConsultInfoModel> searchConsultList(Long patientId, String consultType,
      String consultState) {
    ConsultModelStatus modelStatus = ConsultModelStatus.getByStatus(consultState);
    List<ConsultStatus> consultStatusList = ConsultStatus.getByType(modelStatus);

    BasePage<Consult> page = new BasePage<>(1, 5);
    BasePage<Consult> consultPage = consultService
        .page(page, new QueryWrapper<Consult>()
            .lambda()
            .nested(i -> i.eq(Consult::getPatientId, patientId)
                .eq(Consult::getConsultType, consultType)
                .in(Consult::getStatus, consultStatusList)));

    List<ConsultInfoModel> modelList = consultPage.getRecords().stream()
        .map(consult -> {
          Order order = orderService.selectByConsultId(consult.getConsultId());
          return getConsultInfoModel(consult, order);
        })
        .collect(toList());

    BasePage<ConsultInfoModel> modelBasePage = new BasePage<>(1, 5);
    BeanUtils.copyProperties(consultPage, modelBasePage);
    modelBasePage.setRecords(modelList);

    System.out.println(Collections.unmodifiableCollection(modelBasePage.getRecords()));

    return modelBasePage;
  }

  /**
   * 当前咨询单状态
   * @param doctorId
   * @param patientId
   * @return
   */
  @Override
  public ConsultStatus currentConsultState(Long doctorId, Long patientId) {
    Consult consult = consultService.getOne(new QueryWrapper<Consult>()
        .lambda()
        .nested(i -> i.eq(Consult::getDoctorId, doctorId)
                    .eq(Consult::getPatientId, patientId)));
    return consult.getStatus();
  }

//  @Override
//  public Boolean changeDoctor(String consultId, Long doctorId, Long patientId) {
//    try {
//      // TODO ---- 1. 终止原咨询单terminateConsult（包括订单） 2. 开启新咨询单 ------> todoByliming
//      terminateConsult(consultId);
//      // TODO ---- 默认图文 2 ------> todoByliming
//      createConsult(doctorId, patientId, 2);
//    }catch (Exception e){
//      e.printStackTrace();
//      throw new ServerException(SERVER_ERROR, e.getMessage());
//    }
//    return true;
//  }

//  @Override
//  public Boolean wait(String consultId) {
//    return null;
//  }

//  @Override
//  public Boolean startConsult(String consultId) {
//    try {
//      consultStateLogic.handleAction(START, consultId);
//    }catch (Exception e){
//      e.printStackTrace();
//      throw new ServerException(SERVER_ERROR, e.getMessage());
//    }
//    return true;
//  }

}
