package com.jkys.consult.logic.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jkys.consult.common.bean.DoctorConsultPrice;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.infrastructure.rpc.usercenter.UserCenterUserService;
import com.jkys.consult.logic.DoctorIncomeLogic;
import com.jkys.consult.model.DoctorIncomeModel;
import com.jkys.consult.reponse.DoctorIncomeResponse;
import com.jkys.consult.request.DoctorIncomeRequest;
import com.jkys.consult.request.DoctorPriceRequest;
import com.jkys.consult.request.PageRequest;
import com.jkys.consult.service.DoctorConsultPriceService;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.shine.service.SystemSettingServcie;
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

    page = orderService.pageOrderByStatusAndDuration(OrderStatus.PAYED, date, page);

    List<Order> list = page.getRecords();

    int total = list.stream()
        .mapToInt(Order::getPrice)
        .sum();

    List<DoctorIncomeModel> incomeModelList = list.stream()
        .map(order -> {
          // TODO ---- 转换待做 医生端prd什么时候定下来 需要返回哪些信息------> todoByliming
          DoctorIncomeModel doctorIncomeModel = new DoctorIncomeModel();
          doctorIncomeModel.setPrice(order.getPrice());
          return doctorIncomeModel;
        })
        .collect(Collectors.toList());

    doctorIncomeResponse.setTotal(total);
    doctorIncomeResponse.setIncomeDetails(incomeModelList);
    return doctorIncomeResponse;
  }

  // TODO ---- 顾问和客户的价格怎么设置 ------> todoByliming
  @Override
  public Integer getDoctorPrice(Long doctorId) {
    // TODO ----  ------> todoByliming
//        String doctorId =  ApiGateway.getUserId();
    log.info("查询医生咨询价格设置:{}", doctorId);
    Integer price = doctorConsultPriceService.getDoctorConsultPrice(doctorId);
//    DoctorAdvisoryPrice doctorAdvisoryPrice = systemSettingServcie.queryDoctorPrice();
//    return price != null ? price : appConfig.getCostCoin();
    log.info("getQueryDoctorAdvisoryPrice：服务价格：{},{}", doctorId, price);
    return price != null ? price : 0;
//    DoctorConsultPriceResponse response = new DoctorConsultPriceResponse();
//    // 没有设置咨询价格
//    if (price == null) {
//      response.setPrice(0);
//      response.setUserDefined(false);
//    } else {
//      response.setPrice(price);
//      response.setUserDefined(true);
//    }

    // TODO ---- 验证医生是什么 ------> todoByliming
    // 是否为验证医生
//    BaseUserInfo userInfo = userCenterUserService.findUserById(doctorId);
//    response
//        .setVerified(userInfo != null && userInfo.getStatus() != null && userInfo.getStatus() ==
//            UserStatusEnum.EFFECTIVE.getValue());
//
//    response.setEffectiveRange(doctorAdvisoryPrice.getRange());

  }

  @Override
  public Boolean updateDoctorPrice(DoctorPriceRequest request) {
    Long doctorId = request.getDoctorId();
    // TODO ----  ------> todoByliming
//        String doctorId =  ApiGateway.getUserId();
    Integer price = request.getPrice();
    log.info("修改医生咨询价格设置,{},{}", doctorId, price);

    // TODO ---- 判断参数是否合法 ------> todoByliming
//    DoctorPriceResponse response = new DoctorPriceResponse();
//    if (price == null) {
//      response.setPrice(0);
//      response.setUserDefined(false);
//    } else {
//      response.setPrice(price);
//      response.setUserDefined(true);
//    }

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
