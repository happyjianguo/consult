package com.jkys.consult.logic.impl;

import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.common.constants.Constants.EXPERT_CONSULT_TYPE;
import static com.jkys.consult.common.constants.Constants.PRESCRIBE_CONSULT_TYPE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.COMPLETE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.CREATE;
import static com.jkys.consult.statemachine.enums.ConsultEvents.TERMINATE;
import static com.jkys.consult.statemachine.enums.ConsultStatus.PROCESSING;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.infrastructure.db.mybatisplus.component.CustomSequenceGenerator;
import com.jkys.consult.infrastructure.mapstrut.ConsultInfoMapper;
import com.jkys.consult.infrastructure.rpc.chat.ChatMessageService;
import com.jkys.consult.infrastructure.rpc.usercenter.SpecialDoctorHandler;
import com.jkys.consult.infrastructure.rpc.usercenter.UserCenterUserService;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.logic.ConsultStateLogic;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.reponse.ConsultInfoResponse;
import com.jkys.consult.request.ConsultInfoRequest;
import com.jkys.consult.request.PageRequest;
import com.jkys.consult.service.ConsultService;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.shine.service.AppConfig;
import com.jkys.usercenter.client.domain.user.BaseUserInfo;
import com.jkys.usercenter.client.domain.user.UserExtraInfo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
public class ConsultLogicImpl implements ConsultLogic {

  @Autowired
  ConsultStateLogic consultStateLogic;

  @Autowired
  ConsultService consultService;

  @Autowired
  OrderService orderService;

  @Autowired
  private CustomSequenceGenerator sequenceGenerator;

  @Autowired
  private UserCenterUserService userCenterUserService;

  @Autowired
  ChatMessageService chatMessageService;

  @Resource
  private ConsultInfoMapper consultInfoMapper;

  @Resource
  private AppConfig appConfig;

  private final String[] column = {"hospital"};

  /**
   * 创建咨询单
   */
  @Override
  public String createConsult(Long doctorId, Long patientId, Integer consultType) {
    return createConsult(doctorId, patientId, consultType, null);
  }

  /**
   * 创建咨询单
   */
  @Override
  public String createConsult(Long doctorId, Long patientId, Integer consultType,
      String mallOrderId) {
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
   */
  @Override
  public ConsultInfoModel searchConsultDetail(String consultId) {
    Consult consult = consultService.selectByConsultId(consultId);
    return getConsultInfoModel(consult);
  }

  private ConsultInfoModel getConsultInfoModel(Consult consult) {
    List<ConsultInfoModel> list = getConsultInfoModelList(Collections.singletonList(consult));
    return CollectionUtils.isEmpty(list) ? null : list.get(0);
  }

  private List<ConsultInfoModel> getConsultInfoModelList(List<Consult> consultList) {
    if (CollectionUtils.isEmpty(consultList)) {
      return null;
    }

    List<Long> doctorIds = consultList.stream()
        .map(Consult::getDoctorId)
        .collect(toList());

    List<Long> patientIds = consultList.stream()
        .map(Consult::getPatientId)
        .collect(toList());

    List<String> consultIds = consultList.stream()
        .map(Consult::getConsultId)
        .collect(toList());

    List<Order> orderList = orderService
        .list(new QueryWrapper<Order>().lambda().in(Order::getConsultId, consultIds));

    Map<String, Order> orders = orderList.stream()
        .collect(toMap(Order::getConsultId, Function.identity()));

    BaseUserInfo doctor;
    UserExtraInfo doctorExtra;
    BaseUserInfo patient;
    UserExtraInfo patientExtra;

    Map<Long, BaseUserInfo> doctors = userCenterUserService.findUserByIds(doctorIds);
    Map<Long, UserExtraInfo> doctorExtras = userCenterUserService
        // TODO ---- 额外字段在哪看 ------> todoByliming
        .findUserExtraByUserIds(doctorIds, null);

    Map<Long, BaseUserInfo> patients = userCenterUserService.findUserByIds(patientIds);
    Map<Long, UserExtraInfo> patientExtras = userCenterUserService
        .findUserExtraByUserIds(patientIds, null);

    // TODO ---- 测试用 ------> todoByliming
    if (ObjectUtils.isEmpty(doctors) || doctors.isEmpty()
        || ObjectUtils.isEmpty(doctorExtras) || doctorExtras.isEmpty()
        || ObjectUtils.isEmpty(patients) || patients.isEmpty()
        || ObjectUtils.isEmpty(patientExtras) || patientExtras.isEmpty()) {
//      return null;
      doctors = new HashMap<>();
      doctors.put(consultList.get(0).getDoctorId(), new BaseUserInfo());

      doctorExtras = new HashMap<>();
      Map<String, Object> pairs1 = new HashMap<>();
      pairs1.put("title", "主任");
      pairs1.put("department", "外科");
      pairs1.put("hospital", "人民医院");
      UserExtraInfo userExtraInfo1 = new UserExtraInfo();
      userExtraInfo1.setPairs(pairs1);
      doctorExtras.put(consultList.get(0).getDoctorId(), userExtraInfo1);

      patients = new HashMap<>();
      patients.put(consultList.get(0).getPatientId(), new BaseUserInfo());

      patientExtras = new HashMap<>();
      Map<String, Object> pairs2 = new HashMap<>();
      pairs2.put("age", "11");
      pairs2.put("presentIllness", "现在");
      pairs2.put("pastHistory", "过去");
      pairs2.put("allergyHistory", "过敏");
      UserExtraInfo userExtraInfo2 = new UserExtraInfo();
      userExtraInfo2.setPairs(pairs2);
      patientExtras.put(consultList.get(0).getPatientId(), userExtraInfo2);
    }

    List<ConsultInfoModel> models = new ArrayList<>();
    for (Consult consult : consultList) {
      Order order = orders.get(consult.getConsultId());
      doctor = doctors.get(consult.getDoctorId());
      doctorExtra = doctorExtras.get(consult.getDoctorId());
      patient = patients.get(consult.getPatientId());
      patientExtra = patientExtras.get(consult.getPatientId());

      ConsultInfoModel model = consultInfoMapper
          .toConsultInfoModel(consult, order, doctor, doctorExtra, patient, patientExtra);

      models.add(model);
    }
    return models;
  }

  private ConsultInfoModel getConsultInfoModel2(Consult consult, Order order) {
    ConsultInfoModel consultInfoModel = new ConsultInfoModel();
    BeanUtils.copyProperties(order, consultInfoModel);
    BeanUtils.copyProperties(consult, consultInfoModel);

    String status = consult.getStatus().getType().getName();
    consultInfoModel.setStatus(status);

    return consultInfoModel;
  }

  /**
   * 咨询单列表 分页
   */
  @Override
  public BasePage<ConsultInfoModel> searchConsultList(PageRequest<ConsultInfoRequest> pageRequest) {

    BasePage<Consult> consultPage = consultService.pagePatientConsultByTypeAndState(pageRequest);

    List<ConsultInfoModel> modelList = getConsultInfoModelList(consultPage.getRecords());

    BasePage<ConsultInfoModel> modelBasePage = new BasePage<>(consultPage.getPageNo(),
        consultPage.getPageSize());
    BeanUtils.copyProperties(consultPage, modelBasePage);
    modelBasePage.setRecords(modelList);

    System.out.println(Collections.unmodifiableCollection(modelBasePage.getRecords()));

    return modelBasePage;
  }

  private Consult freeConsult(Long doctorId, Long patientId) {
    return Consult.builder()
        .doctorId(doctorId)
        .patientId(patientId)
        .status(PROCESSING)
        // TODO ---- 这个算什么咨询 ------> todoByliming
        .consultType(EXPERT_CONSULT_TYPE)
        .build();
  }

  private Consult freeConsult(Long doctorId, Long patientId, Integer consultType) {
    return Consult.builder()
        .doctorId(doctorId)
        .patientId(patientId)
        .status(PROCESSING)
        .consultType(consultType)
        .build();
  }

  /**
   * 当前咨询单状态
   */
  @Override
  public Consult currentConsultState(Long doctorId, Long patientId) {
    // 校验用户权限
//    userCenterUserService.checkUserAuthority(doctorId, DOCTOR_TYPE);
//    userCenterUserService.checkUserAuthority(patientId, PATIENT_TYPE);

    if (doctorId.equals(appConfig.getInsideDoctor())
        || doctorId.equals(appConfig.getCustomer())) {
      return freeConsult(doctorId, patientId);
    }

    // 咨询VIP控糖顾问或vip客服时，判断用户是否为VIP
    if ((doctorId.equals(appConfig.getVipConsultant()) ||
        doctorId.equals(appConfig.getVipCustomerServiceId()))
        // TODO ---- 权益需要更改接口， 等 谢春为 提供 ------> todoByliming
        && userCenterUserService.isVipUser(patientId)) {
      return freeConsult(doctorId, patientId);
    }

    Consult consult;
    // 特殊用户组
    if (SpecialDoctorHandler.isSpecialDoctor(doctorId)) {
      consult = consultService.selectByDoctorListAndPatient(
          SpecialDoctorHandler.allSpecialDoctor(), patientId);
    } else {
//      consult = consultService.selectByDoctorAndPatient(doctorId, patientId);
      // 开药门诊
      consult = consultService.selectByPatientAndConsultType(patientId, PRESCRIBE_CONSULT_TYPE);
      if(ObjectUtils.isEmpty(consult)){
        // 专家问诊
        consult = consultService.selectByDoctorAndPatientAndConsultType(doctorId, patientId, EXPERT_CONSULT_TYPE);
        if(ObjectUtils.isEmpty(consult)){
          return null;
        }
      }
    }
    return consult;
  }

  /**
   * 当前咨询单状态
   */
  @Override
  public Consult currentConsultState(Long doctorId, Long patientId, Integer consultType) {
    // 校验用户权限
//    userCenterUserService.checkUserAuthority(doctorId, DOCTOR_TYPE);
//    userCenterUserService.checkUserAuthority(patientId, PATIENT_TYPE);

    if (doctorId.equals(appConfig.getInsideDoctor())
        || doctorId.equals(appConfig.getCustomer())) {
      return freeConsult(doctorId, patientId, consultType);
    }

    // 咨询VIP控糖顾问或vip客服时，判断用户是否为VIP
    if ((doctorId.equals(appConfig.getVipConsultant()) ||
        doctorId.equals(appConfig.getVipCustomerServiceId()))
        // TODO ---- 权益需要更改接口， 等 谢春为 提供 ------> todoByliming
        && userCenterUserService.isVipUser(patientId)) {
      return freeConsult(doctorId, patientId, consultType);
    }

    Consult consult = null;
    // 特殊用户组
    if (SpecialDoctorHandler.isSpecialDoctor(doctorId)) {
      consult = consultService.selectByDoctorListAndPatient(
          SpecialDoctorHandler.allSpecialDoctor(), patientId);
    }
//      consult = consultService.selectByDoctorAndPatient(doctorId, patientId);
    if(PRESCRIBE_CONSULT_TYPE.equals(consultType)){
      // 开药门诊
      consult = consultService.selectByPatientAndConsultType(patientId, PRESCRIBE_CONSULT_TYPE);
    }
    if(EXPERT_CONSULT_TYPE.equals(consultType)){
      // 专家问诊
      consult = consultService.selectByDoctorAndPatientAndConsultType(doctorId, patientId, EXPERT_CONSULT_TYPE);
    }
    return consult;
  }

  @Override
  public ConsultInfoResponse currentConsultModelState(Long doctorId, Long patientId) {
    Consult consult = currentConsultState(doctorId, patientId);
    if (ObjectUtils.isEmpty(consult)) {
      return null;
    }
    return consultInfoMapper.toConsultInfoResponse(consult);
  }

  @Override
  public ConsultInfoResponse currentConsultModelState(Long doctorId, Long patientId, Integer consultType) {
    Consult consult = currentConsultState(doctorId, patientId, consultType);
    if (ObjectUtils.isEmpty(consult)) {
      return null;
    }
    return consultInfoMapper.toConsultInfoResponse(consult);
  }

  @Override
  public Consult unprocessedPrescribeConsult(Long patientId, Integer consultType) {
    Consult consult = consultService.selectByPatientAndConsultType(patientId, PRESCRIBE_CONSULT_TYPE);
    return consult;
  }

  @Override
  public Consult unprocessedExpertConsult(Long doctorId, Long patientId) {
    Consult consult = consultService.selectByDoctorAndPatientAndConsultType(doctorId, patientId, EXPERT_CONSULT_TYPE);
    return consult;
  }

//  public Boolean unprocessedExpertConsult(Long doctorId, Long patientId, Integer consultType) {
//    ConsultInfoResponse response = currentConsultModelState(doctorId, patientId);
//    if (!ObjectUtils.isEmpty(response)
//        && (PROCESSING.getStatus().equals(response.getConsultState())
//        || consultType.equals(response.getConsultType()))) {
//      return true;
//    }
//    return false;
//  }

  @Override
  public boolean triggerTerminateEvent(Long doctorId, Long patient) {
    return checkOverTime(doctorId, patient, "24小时");
  }

  @Override
  public boolean triggerCancelEvent(Long doctorId, Long patient) {
    return checkOverTime(doctorId, patient, "15分钟");
  }

  @Override
  public boolean triggerMayChangeDoctorEvent(Long doctorId, Long patient) {
    return checkOverTime(doctorId, patient, "12小时");
  }

  /**
   * duration 为15分钟，12小时，24小时
   */
  public boolean checkOverTime(Long doctorId, Long patient, String duration) {
    Long lastMessageTimeStamp = chatMessageService.queryLastMessageTimeStamp(doctorId, patient);
    LocalDateTime now = LocalDateTime.now();
    // TODO ---- 判断时间差 now - lastMessageTimeStamp 是否超时 duration ------> todoByliming
    return false;
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
