package com.jkys.consult.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jkys.consult.common.bean.Consult;

public interface ConsultService extends IService<Consult> {

  Consult selectByConsultId(String consultId);

  void updateByConsultId(Consult consult);

  int saveConsult();

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