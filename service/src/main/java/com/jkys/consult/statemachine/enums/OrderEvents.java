package com.jkys.consult.statemachine.enums;

import org.apache.commons.lang3.StringUtils;

public enum OrderEvents {
  CREATE(0, "创建订单"),
  PAY(1, "支付"),
  REFUND(2, "退款"),
  CANCEL(3, "取消订单");

  int code;
  String name;

  OrderEvents(int code, String name) {
    this.code = code;
    this.name = name;
  }

  /**
   * 判断event是否相等
   */
  public static boolean equals(String status, ConsultStatus statusEnum) {
    return StringUtils.equalsIgnoreCase(status, statusEnum.getStatus());

  }

}
