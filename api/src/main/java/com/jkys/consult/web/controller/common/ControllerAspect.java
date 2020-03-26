package com.jkys.consult.web.controller.common;

import com.jkys.consult.web.controller.common.annotation.Anonymous;
import java.lang.reflect.Method;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

/**
 * @author huadi
 * @version 2017/8/25
 */
//@Aspect
//@Order(1)
@Deprecated
public abstract class ControllerAspect {

  private static final Logger log = LoggerFactory.getLogger(ControllerAspect.class);

  @Autowired
  private HttpServletRequest request;

  @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
  public void pointCut() {
  }

  @Before("pointCut()")
  @Order(1)
  public void before(JoinPoint joinPoint) {
    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    if (method.isAnnotationPresent(Anonymous.class)) {
      return;
    }

//    if (isLegalToken(getToken())) {
//      throw new AccessDeniedException();
//    }
//    if (method.isAnnotationPresent(AdminPrivilege.class)) {
//      if (isAdmin(getToken())) {
//        throw new AccessDeniedException();
//      }
//    }
  }

  // NOTE 这里演示了从cookie中拿token，实际情况可能不同
  private String getToken() {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName() != null && "token".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  protected abstract boolean isLegalToken(String token);

  protected abstract boolean isAdmin(String token);

  @After("pointCut()")
  public void after(JoinPoint joinPoint) {
    RequestContext.clear();
  }

  @AfterReturning(pointcut = "pointCut()", returning = "result")
  public void afterReturning(JoinPoint joinPoint, Object result) {
    log.info(joinPoint.getSignature().getName());
  }

  @AfterThrowing(pointcut = "pointCut()", throwing = "t")
  public void afterThrowing(JoinPoint joinPoint, Throwable t) {
    log.error("Exception on {}.", joinPoint.getSignature().getName(), t);
  }
}
