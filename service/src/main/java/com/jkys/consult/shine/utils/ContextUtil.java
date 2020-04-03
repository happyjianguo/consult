package com.jkys.consult.shine.utils;

import com.jkys.phobos.ApiGateway;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 登录信息获取工具
 *
 * @author xiecw
 * @date: 2018/09/04
 */
@Component
public class ContextUtil implements ApplicationContextAware {

  //    @Value("${app.profiles}")
//    private String profiles;

  private static Logger logger = LoggerFactory.getLogger(ContextUtil.class);

  /**
   * 获得当前登录用户的Id
   *
   * @return userId
   */
  public static Long getUserId() {
    try {
      return ApiGateway.getUserId();
    } catch (Exception e) {
      logger.error("读取用户ID失败", e);
    }

    return null;
  }

  private static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if (ContextUtil.applicationContext == null) {
      ContextUtil.applicationContext = applicationContext;
    }
  }

  //获取applicationContext上下文
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  //通过name获取 Bean.
  public static Object getBean(String name) {
    return getApplicationContext().getBean(name);

  }

  //通过class获取Bean.
  public static <T> T getBean(Class<T> clazz) {
    return getApplicationContext().getBean(clazz);
  }

  //通过name,以及Clazz返回指定的Bean
  public static <T> T getBean(String name, Class<T> clazz) {
    return getApplicationContext().getBean(name, clazz);
  }

  // 获取当前环境参数  exp: dev,prod,test
  public static String getActiveProfile() {
    String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
    if (!ArrayUtils.isEmpty(profiles)) {
      return profiles[0];
    }
    return "";
  }

  /**
   * 是否为生产环境
   */
  public static boolean isProduction() {
    return ContextUtil.getActiveProfile().equals("prd");
  }

  public static boolean isPre() {
    return ContextUtil.getActiveProfile().equals("pre");
  }

  /**
   * 是否为测试环境
   */
  public static boolean isQa() {
    return ContextUtil.getActiveProfile().equals("qa");
  }

  /**
   * 是否为开发环境
   */
  public static boolean isDevelop() {
    return ContextUtil.getActiveProfile().equals("dev");
  }

}
