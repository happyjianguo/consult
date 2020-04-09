package com.jkys.consult.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkys.consult.common.bean.DoctorConsultPrice;
import com.jkys.consult.infrastructure.db.mapper.DoctorConsultPriceMapper;
import com.jkys.consult.service.DoctorConsultPriceService;
import org.springframework.stereotype.Service;

@Service
public class DoctorConsultPriceServiceImpl extends
    ServiceImpl<DoctorConsultPriceMapper, DoctorConsultPrice> implements
    DoctorConsultPriceService {

  @Override
  public Integer getDoctorConsultPrice(Long doctorId) {
    return this.baseMapper.queryDoctorAdvisoryPrice(doctorId);
  }

  @Override
  public Integer updateDoctorConsultPrice(Long doctorId, Integer price) {
    return this.baseMapper.modifyAdvisoryPrice(doctorId, price);
  }

  @Override
  public Integer addAdvisoryPrice(Long doctorId, Integer price) {
    return this.baseMapper.addAdvisoryPrice(doctorId, price);
  }

//  @Override
//  public List<CoinOrderModel> batchQueryAdvisoryPrice(List<Long> list) {
//    return this.baseMapper.batchQueryAdvisoryPrice(list);
//  }
}
