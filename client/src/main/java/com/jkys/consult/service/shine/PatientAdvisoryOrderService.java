package com.jkys.consult.service.shine;

import com.jkys.phobos.annotation.Service;

/**
 * 咨询支付订单管理
 * @author yangZh
 * @since 2018/8/31
 **/
@Service("jkys-shine.PatientAdvisoryOrderService:1.0.0")
public interface PatientAdvisoryOrderService {
   /**
    * 病人 - 咨询支付单列表
    * @param request status
    * @return list
    */
//   List<OrderInfoModel> queryPatientInfoOrderList(OrderInfoRequest request);
//
//   /**
//    * 医生-我的订单
//    * @param request status
//    * @return list
//    */
//   OrderResult queryDoctorInfoOrderList(OrderInfoRequest request);
//
//   /**
//    * 根据订单编号和类型查找 咨询订单
//    * @param orderNum 订单编号
//    * @param type 订单类型 PAY FREE BACK
//    * @return order
//    */
//   OrderInfoModel queryOrderOne(String orderNum, String type) ;

   /**
    * 退款退订单
    * @param orderNum 订单编号
    * @return Boolean
    */
   Boolean backOrder(String orderNum) ;
}
