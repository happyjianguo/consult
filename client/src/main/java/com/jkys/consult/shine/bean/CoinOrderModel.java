package com.jkys.consult.shine.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 咨询糖币支付信息
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoinOrderModel {

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
  private String bizCode;

  /**
   * 支付信息串
   */
  private String payString;
  /**
   * 当前咨询单id
   */
  private Long infoId;
  /**
   * 当前支付单id
   */
  private Long orderId;
  /**
   * 当前咨询单咨询价格
   */
  private Integer amount;
  /**
   * 本次支付云币余结果 1 成功 0 失败 2余额不足
   */
  private Integer coinPay = 0;

  /**
   * 为了兼容消息发送是否是服务端还是前端发送，如果服务端已发送，告诉前端
   */
  private Boolean hasSent = false;
}
