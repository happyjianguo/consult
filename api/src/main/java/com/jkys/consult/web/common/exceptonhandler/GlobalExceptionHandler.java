package com.jkys.consult.web.common.exceptonhandler;

import static com.jkys.consult.common.component.CodeMsg.PARAMS_ERROR;
import static com.jkys.consult.common.component.CodeMsg.PARAMS_ERROR_4_RPC;
import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;

import com.jkys.consult.common.component.Result;
import com.jkys.consult.exception.ServerException;
import com.jkys.consult.exception.ValidationException;
import com.jkys.phobos.exception.ServiceError;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
    e.printStackTrace();
      // 参数校验异常
    if (e instanceof ValidationException) {
      ValidationException ex = (ValidationException) e;
      return Result.error(PARAMS_ERROR.fillArgs(ex.getParamErrorMessage()));
      // 已经被拦截过一次，给rpc的异常
    } else if (e instanceof ServerException) {
      ServerException ex = (ServerException) e;
      return Result.error(SERVER_ERROR.fillArgs(ex.getErrorMessage()));
      // 已经被拦截过一次，给rpc的异常
    }else if (e instanceof ServiceError) {
      ServiceError ex = (ServiceError) e;
      return Result.error(PARAMS_ERROR_4_RPC.fillArgs(ex.getMessage()));
    } else if (e instanceof BindException) {
      BindException ex = (BindException) e;
      List<ObjectError> errors = ex.getAllErrors();
      ObjectError error = errors.get(0);
      String msg = error.getDefaultMessage();
      return Result.error(SERVER_ERROR.fillArgs(msg));
    } else {
      return Result.error(SERVER_ERROR);
    }
  }
}
