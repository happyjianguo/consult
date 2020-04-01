package com.jkys.consult.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jkys.consult.common.bean.ConsultStateMachineContext;

public interface ConsultStateMachineContextService extends IService<ConsultStateMachineContext> {

  ConsultStateMachineContext selectByBizCode(String contextObj);

  void insertConsultContext(ConsultStateMachineContext bosmContext);

  void updateByBizCode(ConsultStateMachineContext queryResult);

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