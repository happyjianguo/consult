package com.jkys.consult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkys.consult.common.bean.ConsultStateMachineContext;
import com.jkys.consult.infrastructure.db.mapper.ConsultStateMachineContextMapper;
import com.jkys.consult.service.ConsultStateMachineContextService;
import org.springframework.stereotype.Service;

@Service
public class ConsultStateMachineContextServiceImpl extends
    ServiceImpl<ConsultStateMachineContextMapper, ConsultStateMachineContext> implements
    ConsultStateMachineContextService {

  @Override
  public ConsultStateMachineContext selectByBizCode(String contextObj) {
    ConsultStateMachineContext context = this.getOne(new QueryWrapper<ConsultStateMachineContext>().lambda().eq(ConsultStateMachineContext::getBizCode, contextObj));
    return context;
  }

  @Override
  public void insertConsultContext(ConsultStateMachineContext context) {
    context.insert();
  }

  @Override
  public void updateByBizCode(ConsultStateMachineContext context) {
    context.insertOrUpdate();
  }
}
