package com.jkys.consult.exception;

import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;

import com.jkys.consult.common.component.CodeMsg;
import com.jkys.phobos.exception.ServiceError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private CodeMsg codeMsg;

  private String errorMessage;

  public ServerException(CodeMsg codeMsg) {
    super(codeMsg.getMsg());
    this.codeMsg = codeMsg;
  }

  public ServerException(CodeMsg codeMsg, String errorMessage) {
    super(codeMsg.fillArgs(errorMessage).getMsg());
    this.codeMsg = codeMsg;
    this.errorMessage = errorMessage;
  }

  /**
   * 转换异常
   */
  public ServiceError getServiceError() {
    return new ServiceError(String.valueOf(SERVER_ERROR.getCode()),
        SERVER_ERROR.fillArgs(this.errorMessage).getMsg());
  }

}
