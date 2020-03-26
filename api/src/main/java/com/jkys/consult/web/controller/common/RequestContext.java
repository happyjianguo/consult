package com.jkys.consult.web.controller.common;

import com.jkys.consult.common.model.User;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huadi
 * @version 2017/2/10
 */
@Deprecated
public class RequestContext {

  private static final String KEY_USER = "user";


  private static ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

  private static Map<String, Object> getContextMap() {
    Map<String, Object> map = context.get();
    if (map == null) {
      map = new HashMap<>();
      context.set(map);
    }
    return map;
  }

  public static Object get(String key) {
    return getContextMap().get(key);
  }

  public static void put(String key, Object value) {
    getContextMap().put(key, value);
  }

  static void putUser(User user) {
    put(KEY_USER, user);
  }

  /**
   * 普通方法需要传token才能访问，校验token时，会拿到用户的ID。 使用{@link com.jkys.consult.web.controller.common.annotation.Anonymous}修饰的方法不需要传token，但假如token有效，也会尝试转换成user
   * ID。 当此两种情况发生时，会将用户ID放到context中，方便controller获取用户ID。
   *
   * @return 普通方法中，肯定会返回一个userId；anonymous方法中，有可能返回null
   */
  public static User getUser() {
    return (User) get(KEY_USER);
  }

  static void clear() {
    Map<String, Object> ctx = context.get();
    if (ctx != null) {
      ctx.clear();
    }
  }
}
