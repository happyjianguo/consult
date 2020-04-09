package com.jkys.consult.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jkys.consult.common.bean.DoctorConsultPrice;

public interface DoctorConsultPriceService extends IService<DoctorConsultPrice> {

  Integer getDoctorConsultPrice(Long doctorId);

  Integer updateDoctorConsultPrice(Long doctorId, Integer price);

  Integer addAdvisoryPrice(Long doctorId, Integer price);

//  List<CoinOrderModel> batchQueryAdvisoryPrice(List<Long> list);
}