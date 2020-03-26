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
