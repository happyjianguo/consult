package com.jkys.consult.logic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jkys.consult.common.BasePage;
import com.jkys.consult.common.bean.DoctorConsultPrice;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.logic.DoctorIncomeLogic;
import com.jkys.consult.reponse.DoctorConsultPriceResponse;
import com.jkys.consult.request.DoctorIncomeRequest;
import com.jkys.consult.request.DoctorPriceRequest;
import com.jkys.consult.request.PageRequest;
import com.jkys.consult.service.DoctorConsultPriceService;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.model.DoctorIncomeModel;
import com.jkys.consult.reponse.DoctorIncomeResponse;
import com.jkys.consult.shine.service.SystemSettingServcie;
import com.jkys.consult.shine.service.UserCenterUserService;
import com.jkys.consult.shine.utils.DateUtils;
import com.jkys.consult.statemachine.enums.OrderStatus;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DoctorIncomeLogicImpl implements DoctorIncomeLogic {

  @Autowired
  private DoctorConsultPriceService doctorConsultPriceService;
  @Autowired
  private OrderService orderService;
  @Autowired
  private SystemSettingServcie systemSettingServcie;
  @Autowired
  private UserCenterUserService userCenterUserService;
  // TODO ---- 干掉 ------> todoByliming
//    @Autowired
//    private SystemSettingServcie systemSettingServcie;

  @Override
  public DoctorIncomeResponse queryDoctorIncome(PageRequest pageRequest) {
    DoctorIncomeRequest request = (DoctorIncomeRequest) pageRequest.getRequest();
    BasePage page = pageRequest.getPage();
    return queryDoctorIncome(request, page);
  }

  // TODO ---- 是否有收入明细接口 ------> todoByliming
  @Override
  public DoctorIncomeResponse queryDoctorIncome(DoctorIncomeRequest request, BasePage page) {
    Long doctorId = request.getDoctorId();
    // TODO ---- Long userId = ApiGateway.getUserId(); ------> todoByliming
//    Long doctorId = ContextUtil.getUserId();
    log.info("查询医生收入明细,user:{}", doctorId);
    String date = request.getDate();

    DoctorIncomeResponse doctorIncomeResponse = new DoctorIncomeResponse();

    String startDate = DateUtils.getFirstDayOfMonth(date);
    String endDate = DateUtils.getFirstDayOfNextMonth(date);

    page = orderService.page(page,
        new QueryWrapper<Order>().lambda()
            .nested(i -> i.eq(Order::getStatus, OrderStatus.PAYED)
                .between(Order::getGmtCreate, startDate, endDate)));

//    List<Order> list = orderService.list(new QueryWrapper<Order>().lambda()
//        .nested(i -> i.eq(Order::getStatus, OrderStatus.PAYED)
//            .between(Order::getGmtCreate, startDate, endDate)));

    List<Order> list = page.getRecords();

    int total = list.stream()
        .mapToInt(Order::getPrice)
        .sum();

    List<DoctorIncomeModel> incomeModelList = list.stream()
        .map(order -> {
          // TODO ---- 转换待做 ------> todoByliming
          DoctorIncomeModel doctorIncomeModel = new DoctorIncomeModel();
          doctorIncomeModel.setPrice(order.getPrice());
          return doctorIncomeModel;
        })
        .collect(Collectors.toList());

    doctorIncomeResponse.setTotal(total);
    doctorIncomeResponse.setIncomeDetails(incomeModelList);
    return doctorIncomeResponse;

//    BasePage<PatientAdvisoryOrder> doctorIncome = patientAdvisoryOrderMapper
//        .queryDoctorIncome(doctorId, startDate, endDate, request);

//    Integer total = patientAdvisoryOrderMapper
//        .queryDoctorTotalIncome(doctorId, startDate, endDate);

//    if (doctorIncome == null || doctorIncome.size() == 0) {
//      return doctorIncomeResponse;
//    }


/*
    // 转换输出
    List<DoctorIncomeModel> incomeDetail = new ArrayList<>(doctorIncome.size());
    for (PatientAdvisoryOrder advisoryOrder : doctorIncome) {
      DoctorIncomeModel doctorIncomeModel = new DoctorIncomeModel();
      doctorIncomeModel
          .setTime(DateUtils.formatDate(advisoryOrder.getGmtCreate(), DateUtils.MMdd_SPLIT));
      doctorIncomeModel.setPrice(advisoryOrder.getAmount());
      doctorIncomeModel.setId(advisoryOrder.getId());
      doctorIncomeModel.setPatientId(advisoryOrder.getPatientId());
      doctorIncomeModel.setDoctorId(advisoryOrder.getDoctorId());
      // 如果退款价格为负数,价格为负数
      // TODO ---- 退款逻辑，重写 ------> todoByliming
      if (OrderType.BACK.name().equals(advisoryOrder.getType())) {
        doctorIncomeModel.setPrice(-doctorIncomeModel.getPrice());
      }
      doctorIncomeModel.setTitle("图文咨询 " + advisoryOrder.getBizCode());
      // 添加doctor_id patient_id 跳转到详情页面
      incomeDetail.add(doctorIncomeModel);
    }
    */
//    doctorIncomeResponse.setTotal(total);
//    doctorIncomeResponse.setIncomeDetail(incomeDetail);
//    return doctorIncomeResponse;
  }

  @Override
  public DoctorConsultPriceResponse getDoctorPrice(Long doctorId) {
    // TODO ----  ------> todoByliming
//        String doctorId =  ApiGateway.getUserId();
    log.info("查询医生咨询价格设置:{}", doctorId);
    Integer price = doctorConsultPriceService.getDoctorConsultPrice(doctorId);
//    DoctorAdvisoryPrice doctorAdvisoryPrice = systemSettingServcie.queryDoctorPrice();

    DoctorConsultPriceResponse response = new DoctorConsultPriceResponse();
    // 没有设置咨询价格
    if (price == null) {
      response.setPrice(0);
      response.setUserDefined(false);
    } else {
      response.setPrice(price);
      response.setUserDefined(true);
    }
    log.info("getQueryDoctorAdvisoryPrice：服务价格：{},{}", doctorId, response.getPrice());

    // TODO ---- 验证医生是什么 ------> todoByliming
    // 是否为验证医生
//    BaseUserInfo userInfo = userCenterUserService.findUserById(doctorId);
//    response
//        .setVerified(userInfo != null && userInfo.getStatus() != null && userInfo.getStatus() ==
//            UserStatusEnum.EFFECTIVE.getValue());
//
//    response.setEffectiveRange(doctorAdvisoryPrice.getRange());

    // TODO ---- 响应内容，待定 ------> todoByliming
    return response;
  }

  @Override
  public Boolean updateDoctorPrice(DoctorPriceRequest request) {
    Long doctorId = request.getDoctorId();
    // TODO ----  ------> todoByliming
//        String doctorId =  ApiGateway.getUserId();
    Integer price = request.getPrice();
    log.info("修改医生咨询价格设置,{},{}", doctorId, price);

    DoctorConsultPrice doctorConsultPrice = DoctorConsultPrice.builder()
        .doctorId(doctorId)
        .price(price)
        .build();
    boolean result = doctorConsultPriceService.saveOrUpdate(doctorConsultPrice,
        new UpdateWrapper<DoctorConsultPrice>().lambda()
            .eq(DoctorConsultPrice::getDoctorId, doctorId));
    return result;

    // TODO ---- 设置医生默认价格 ------> todoByliming
//        DoctorAdvisoryPrice doctorAdvisoryPrice = systemSettingServcie.queryDoctorPrice();
//    DoctorAdvisoryPrice doctorAdvisoryPrice = new DoctorAdvisoryPrice();

    // TODO ---- 验证参数放在 DoctorPriceRequest ------> todoByliming
//      if (price == null || price < doctorAdvisoryPrice.getMin() || price > doctorAdvisoryPrice
////          .getMax()) {
////          return ResponseServiceUtils.invalidParams();
////      }
    // 判断记录是否存在，不存在加一条
//    Integer beforePrice = doctorConsultPriceService.queryDoctorAdvisoryPrice(doctorId);
//    if (beforePrice == null) {
//      Integer cnt = doctorConsultPriceService.addAdvisoryPrice(doctorId, price);
//      if (cnt != 1) {
//        return ResponseServiceUtils.failure();
//      }
//    } else {
//      Integer count = doctorConsultPriceService.modifyAdvisoryPrice(doctorId, price);
//      if (count != 1) {
//        return ResponseServiceUtils.failure();
//      }
//    }
  }
}
