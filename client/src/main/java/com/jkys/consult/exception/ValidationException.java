package com.jkys.consult.exception;

import com.jkys.consult.common.CodeMsg;
import com.jkys.phobos.exception.ServiceError;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationException extends ServerException {

  private static final long serialVersionUID = 1L;

  private CodeMsg codeMsg;

  private String paramErrorMessage;

  public ValidationException(CodeMsg codeMsg) {
    super(codeMsg);
    this.codeMsg = codeMsg;
  }

  public ValidationException(CodeMsg codeMsg, String paramErrorMessage) {
    super(codeMsg.fillArgs(paramErrorMessage));
    this.codeMsg = codeMsg;
    this.paramErrorMessage = paramErrorMessage;
  }

  /**
   * 转换异常
   */
  @Override
  public ServiceError getServiceError() {
    return new ServiceError(String.valueOf(CodeMsg.PARAMS_ERROR.getCode()),
        CodeMsg.PARAMS_ERROR.fillArgs(this.paramErrorMessage).getMsg());
  }

}
