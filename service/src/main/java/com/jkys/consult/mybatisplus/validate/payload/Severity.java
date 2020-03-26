package com.jkys.consult.mybatisplus.validate.payload;

import javax.validation.Payload;

/**
 * 通过payload属性来指定默认错误严重级别的示例
 */
public class Severity {

  public static class Info implements Payload {

  }

  ;

  public static class Error implements Payload {

  }

  ;
}