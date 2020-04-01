package com.jkys.consult.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfoModel {

  /**
   * 咨询单号
   */
  private String consultId;

  /**
   * 订单号
   */
  private String orderId;

  /**
   * 价格
   */
  private String price;

  /**
   * 当前咨询状态,根据咨询单和订单获取
   */
  private String state;

  /**
   * 下单时间
   */
  private String startTime;

}
