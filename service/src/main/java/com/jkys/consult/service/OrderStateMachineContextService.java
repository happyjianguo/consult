package com.jkys.consult.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jkys.consult.common.bean.OrderStateMachineContext;

public interface OrderStateMachineContextService extends IService<OrderStateMachineContext> {

  OrderStateMachineContext selectByBizCode(String contextObj);

  void insertOrderContext(OrderStateMachineContext bosmContext);

  void updateByBizCode(OrderStateMachineContext queryResult);

//  BasePage<User> mySelectPage(@Param("pg") BasePage<User> myPage);
//
//  User queryUserName(String s);
//
//  void deleteByPrimaryKey(Integer integer);
//
//  void insertUser(User user);
//
//  void insertSelective(User user);
//
//  void updateByPrimaryKeySelective(User user);
//
//  void updateByPrimaryKey(User user);
//
//  void selectByPrimaryKey(Integer integer);
}