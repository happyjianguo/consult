package com.jkys.consult.common;

import static com.jkys.consult.common.CodeMsg.SUCCESS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {

  /**
   * 执行是否成功
   */
  private boolean success = false;

  /**
   * 执行结果返回的code 见ResultCode
   */
  private int code;

  /**
   * 执行结果返回的消息描述
   */
  private String message;

  /**
   * 默认的返回结果
   */
  private T data;

  /**
   * 成功时候的调用
   */
  public static <T> Result<T> success(T data) {
    return new Result<T>(data);
  }

  /**
   * 失败时候的调用
   */
  public static <T> Result<T> error(CodeMsg codeMsg) {
    return new Result<T>(codeMsg);
  }

  private Result(T data) {
    this.code = SUCCESS.getCode();
    this.message = SUCCESS.getMsg();
    this.data = data;
    this.success = true;
  }

  private Result(int code, String msg) {
    this.code = code;
    this.message = msg;
  }

  private Result(CodeMsg codeMsg) {
    if (codeMsg != null) {
      this.code = codeMsg.getCode();
      this.message = codeMsg.getMsg();
    }
  }


}
