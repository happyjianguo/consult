package com.jkys.consult.infrastructure.db.mapper;

import com.jkys.consult.common.bean.User;
import com.jkys.consult.common.component.SuperMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper extends SuperMapper<User> {

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