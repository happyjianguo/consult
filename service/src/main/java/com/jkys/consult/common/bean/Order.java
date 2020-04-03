package com.jkys.consult.common.bean;

import static com.jkys.consult.statemachine.enums.OrderStatus.INIT;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jkys.consult.common.BaseEntity;
import com.jkys.consult.statemachine.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "t_order")
public class Order extends BaseEntity<Order> {

  /**
   * 咨询单号
   */
  private String consultId;

  /**
   * 支付订单号
   */
  //TODO 等同于patient_advisory_order | patient_advisory_info的order_num
  private String orderId;

  /**
   * 当前订单状态
   */
  @Default
  private OrderStatus status = INIT;

  /**
   * 咨询价格（分）
   */
  @TableField(value = "amount")
  private Integer price;

  /**
   * 充值发起的支付串
   */
  private String payString;

  /**
   * 本次支付云币结果 1 成功 0 失败 2余额不足
   */
  @Default
  private Integer coinPay = 0;
}
