package com.jkys.consult.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.statemachine.enums.OrderStatus;
import java.util.List;

public interface OrderService extends IService<Order> {

  Order selectByConsultId(String consultId);

  void updateByConsultId(Order order);

  Order selectByOrderId(String consultId);

  void updateByOrderId(Order order);

  void updateOrderPaying(Integer cost, String bizCode, String payString);

  BasePage<Order> pageOrderByStatusAndDuration(OrderStatus status, String date, BasePage page);

  List<Order> searchOrderListByStatusAndDuration(OrderStatus status, String date);

  BasePage<Order> pageOrderByPatientAndStatus(Long patientId, OrderStatus status, BasePage page);

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