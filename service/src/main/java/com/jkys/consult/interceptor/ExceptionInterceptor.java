package com.jkys.consult.interceptor;

import static com.jkys.consult.common.component.CodeMsg.PARAMS_ERROR;
import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;

import com.jkys.consult.exception.ServerException;
import com.jkys.consult.exception.ValidationException;
import com.jkys.phobos.exception.ServiceError;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class ExceptionInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    Object result;
    try {
      result = invocation.proceed();
    } catch (ValidationException paramEx) {
      log.warn(paramEx.getMessage(), paramEx);
      throw new ServiceError(String.valueOf(PARAMS_ERROR.getCode()),
          PARAMS_ERROR.fillArgs(paramEx.getParamErrorMessage()).getMsg());
    } catch (ServerException e) {
      log.warn(e.getMessage(), e);
      throw new ServiceError(String.valueOf(SERVER_ERROR.getCode()),
          SERVER_ERROR.fillArgs(e.getErrorMessage()).getMsg());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
//      return Result.error(SERVER_ERROR.fillArgs(e.getMessage()));
      throw new ServiceError(String.valueOf(SERVER_ERROR.getCode()),
          SERVER_ERROR.fillArgs(e.getMessage()).getMsg());
    }
    return result;
  }
}
