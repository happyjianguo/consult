package com.jkys.consult.web.common;
//
//import com.jkys.consult.web.common.exception.AccessDeniedException;
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
///**
// * @author huadi
// * @version 2017/2/9
// */
//@RestControllerAdvice
@Deprecated
public class ResponseExceptionHandler{}
//public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
//
//  private static final Logger log = LoggerFactory.getLogger("exceptionLog");
//
//  private void doBeforeReturn(Exception e) {
//    log.error("Exception occurred:", e);
//  }
//
//  private void doBeforeReturn(BaseException e) {
//    log.error("Exception occurred, debug info:\n{}", e.getMessage(), e);
//  }
//
//
//  @ExceptionHandler(AccessDeniedException.class)
//  @ResponseStatus(HttpStatus.FORBIDDEN)
//  public ExceptionResponse accessDeniedException(AccessDeniedException e) {
//    doBeforeReturn(e);
//    return new ExceptionResponse(e);
//  }
//
//
//  @ExceptionHandler(Exception.class)
//  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//  public ExceptionResponse exception(Exception e) {
//    doBeforeReturn(e);
//    return new ExceptionResponse("服务器发生了未知错误，请联系管理员。");
//  }
//
//
//  public static class ExceptionResponse {
//
//    private String message;
//
//    private String stackTrace;
//
//    public ExceptionResponse(String message) {
//      this.message = message;
//    }
//
//    public ExceptionResponse(BaseException e) {
//      this.message = e.getMessage();
//    }
//
//    public ExceptionResponse(Exception e) {
//      message = e.getMessage();
//      stackTrace = ExceptionUtils.getStackTrace(e);
//    }
//
//    public String getMessage() {
//      return message;
//    }
//
//    public void setMessage(String message) {
//      this.message = message;
//    }
//
//    public String getStackTrace() {
//      return stackTrace;
//    }
//
//    public void setStackTrace(String stackTrace) {
//      this.stackTrace = stackTrace;
//    }
//  }
//}
