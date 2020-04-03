package com.jkys.consult.shine.enums;

public interface ExceptionMessage {
  /**咨询订单**/
  String INVALID_ADVISORY = "无效的咨询,请退出重试";
  String USING_ADVISORY = "咨询已开始，请勿重复发起";
  String INVALID_ORDER = "无效的订单";
  String COIN_EXIST = "当前云币余额充足，请用余额支付";
  String ASK_PAY_FAIL = "请求支付失败";
  String COIN_FAIL = "糖币支付失败";
  String ORDER_PAYING = "订单支付中";
  String ORDER_PAY_BUSY = "频繁支付，请稍候!";
  String ORDER_BACK = "订单已退款!";
  String OVER_ADVISORY = "没有可结束的咨询，请勿重复点击";
  String DOCTOR_PRICE = "";
  String USER_INFO_INIT = "本次咨询信息已录入完成";
  String NO_ORDERS = "没有订单信息";

  String RELOGIN = "请重新登录";
  String INVALID_TOKNE_RELOGIN = "token无效";
  String INVALID_USER_STATUS = "无效的用户";
  String USER_NOT_EXIST = "用户不存在";
  String PASSWORD_ERROR = "用户名或密码错误！";

  String UNKNOWN_OBJECT = "未知对象！";
}
