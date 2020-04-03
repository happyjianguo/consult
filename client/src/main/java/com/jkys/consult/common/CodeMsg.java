package com.jkys.consult.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CodeMsg {

  private int code;
  private String msg;

  //通用的错误码
  public static CodeMsg SUCCESS = new CodeMsg(200, "成功");
  public static CodeMsg PARAMS_ERROR = new CodeMsg(500101, "参数校验异常：%s");
  public static CodeMsg PARAMS_ERROR_4_RPC = new CodeMsg(500102, "%s");
  public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常: %s");
  public static CodeMsg SPEC_PATTERN_ERROR = new CodeMsg(500200, "未匹配到规格单位名称");

//  FAIL(-1000,"失败"),
//  TRY_AGAIN(3000,"请重试"),

  /**咨询订单**/
  public static CodeMsg INVALID_ADVISORY = new CodeMsg(3001,"无效的咨询,请退出重试");
  public static CodeMsg USING_ADVISORY = new CodeMsg(3002,"咨询已开始，请勿重复发起");
  public static CodeMsg INVALID_ORDER = new CodeMsg(3003,"无效的订单");
  public static CodeMsg COIN_EXIST = new CodeMsg(3004,"当前云币余额充足，请用余额支付");
  public static CodeMsg ASK_PAY_FAIL = new CodeMsg(3005,"请求支付失败");
  public static CodeMsg COIN_FAIL = new CodeMsg(3006,"糖币支付失败");
  public static CodeMsg ORDER_PAYING = new CodeMsg(3007,"订单支付中");
  public static CodeMsg ORDER_PAY_BUSY = new CodeMsg(3008,"频繁支付，请稍候!");
  public static CodeMsg ORDER_BACK = new CodeMsg(3009,"订单已退款!");
  public static CodeMsg OVER_ADVISORY = new CodeMsg(3010,"没有可结束的咨询，请勿重复点击");
  public static CodeMsg DOCTOR_PRICE = new CodeMsg(3011,"");
  public static CodeMsg USER_INFO_INIT = new CodeMsg(3012,"本次咨询信息已录入完成");
  public static CodeMsg NO_ORDERS = new CodeMsg(3013,"没有订单信息");

  public static CodeMsg RELOGIN = new CodeMsg(4000,"请重新登录");
  public static CodeMsg INVALID_TOKNE_RELOGIN = new CodeMsg(4100,"token无效");
  public static CodeMsg INVALID_USER_STATUS = new CodeMsg(4101,"无效的用户");
  public static CodeMsg USER_NOT_EXIST = new CodeMsg(4102,"用户不存在");
  public static CodeMsg PASSWORD_ERROR = new CodeMsg(4103,"用户名或密码错误！");
  public static CodeMsg UNKNOWN_OBJECT = new CodeMsg(5300,"未知对象！");

  public CodeMsg fillArgs(Object... args) {
    int code = this.code;
    String message = String.format(this.msg, args);
    return new CodeMsg(code, message);
  }

  @Override
  public String toString() {
    return "CodeMsg [code=" + code + ", msg=" + msg + "]";
  }


}
