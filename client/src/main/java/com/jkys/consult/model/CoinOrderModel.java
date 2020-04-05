package com.jkys.consult.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 咨询糖币支付信息
 *
 * @author yangZh
 * @since 2018/7/13
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinOrderModel/* extends BaseResponse */{

  /**
   * 云币余额
   */
  private Integer coin;

  /**
   * 医生定价
   */
  private Integer price;

  /**
   * 原云币价格
   */
  private Integer originalCoin;

  /**
   * 支付订单号
   */
//  private String bizCode;

  /**
   * 支付信息串
   */
  private String payString;

  /**
   * 当前咨询单id
   */
  private Long consultId;

  /**
   * 当前支付单id
   */
  private String orderId;

  /**
   * 本次支付云币余结果 1 成功 0 失败 2余额不足
   */
  private Integer coinPay = 0;

}
