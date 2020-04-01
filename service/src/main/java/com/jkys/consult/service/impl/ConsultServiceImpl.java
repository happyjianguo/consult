package com.jkys.consult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.infrastructure.db.mapper.ConsultMapper;
import com.jkys.consult.service.ConsultService;
import org.springframework.stereotype.Service;

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
  public int saveConsult(){
    return this.baseMapper.insert(new Consult());
  }

}
