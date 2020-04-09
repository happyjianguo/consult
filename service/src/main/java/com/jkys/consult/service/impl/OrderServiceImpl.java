package com.jkys.consult.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkys.consult.common.component.BasePage;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.infrastructure.db.mapper.OrderMapper;
import com.jkys.consult.service.OrderService;
import com.jkys.consult.shine.utils.DateUtils;
import com.jkys.consult.statemachine.enums.OrderStatus;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends
    ServiceImpl<OrderMapper, Order> implements
    OrderService {

  @Override
  public Order selectByConsultId(String consultId) {
    Order context = this.getOne(new QueryWrapper<Order>().lambda().eq(Order::getConsultId, consultId));
    return context;
  }

  @Override
  public void updateByConsultId(Order order) {
    order.insertOrUpdate();
  }

  @Override
  public Order selectByOrderId(String orderId) {
    Order context = this.getOne(new QueryWrapper<Order>().lambda().eq(Order::getOrderId, orderId));
    return context;
  }

  @Override
  public void updateByOrderId(Order order) {
    this.update(order, new UpdateWrapper<Order>().lambda().eq(Order::getOrderId, order.getOrderId()));
  }

  @Override
  public void updateOrderPaying(Integer cost, String bizCode, String payString) {
    // TODO ---- cost是否保存待定 ------> todoByliming
    Order order = Order.builder()
        .orderId(bizCode)
        .payString(payString)
        .build();
    this.update(order, new UpdateWrapper<Order>().lambda().eq(Order::getOrderId, order.getOrderId()));
  }

  @Override
  public BasePage<Order> pageOrderByStatusAndDuration(OrderStatus status, String date, BasePage page) {
    String startDate = DateUtils.getFirstDayOfMonth(date);
    String endDate = DateUtils.getFirstDayOfNextMonth(date);

    page = this.page(page,
        new QueryWrapper<Order>().lambda()
            .nested(i -> i.eq(Order::getStatus, status)
                .between(Order::getGmtCreate, startDate, endDate)));
    return page;
  }

  @Override
  public List<Order> searchOrderListByStatusAndDuration(OrderStatus status, String date) {
    String startDate = DateUtils.getFirstDayOfMonth(date);
    String endDate = DateUtils.getFirstDayOfNextMonth(date);

    List<Order> list = this.list(new QueryWrapper<Order>().lambda()
        .nested(i -> i.eq(Order::getStatus, status)
            .between(Order::getGmtCreate, startDate, endDate)));
    return list;
  }

  @Override
  public BasePage<Order> pageOrderByPatientAndStatus(Long patientId, OrderStatus status,
      BasePage page) {

    page = this.page(page,
        new QueryWrapper<Order>().lambda()
            .nested(i -> i.eq(Order::getStatus, status)
                .eq(Order::getPatientId, patientId)));
    return page;
  }

}
