package com.jkys.consult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkys.consult.common.bean.OrderStateMachineContext;
import com.jkys.consult.infrastructure.db.mapper.OrderStateMachineContextMapper;
import com.jkys.consult.service.OrderStateMachineContextService;
import org.springframework.stereotype.Service;

@Service
public class OrderStateMachineContextServiceImpl extends
    ServiceImpl<OrderStateMachineContextMapper, OrderStateMachineContext> implements
    OrderStateMachineContextService {

  @Override
  public OrderStateMachineContext selectByBizCode(String contextObj) {
    OrderStateMachineContext context = this.getOne(new QueryWrapper<OrderStateMachineContext>().lambda().eq(OrderStateMachineContext::getBizCode, contextObj));
    return context;
  }

  @Override
  public void insertOrderContext(OrderStateMachineContext context) {
    context.insert();
  }

  @Override
  public void updateByBizCode(OrderStateMachineContext context) {
    context.insertOrUpdate();
  }
}
