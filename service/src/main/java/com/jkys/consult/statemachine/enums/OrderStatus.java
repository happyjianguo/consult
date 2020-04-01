package com.jkys.consult.statemachine.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

public enum OrderStatus {
  INIT(1, "初始"),
  WAIT_FOR_PAY(2, "待支付"),
  CANCELED(3, "已取消"),
  PAYED(4, "已支付"),
  REFUNDED(5, "已退款");

  @Getter
  int code;
  @Getter
  @EnumValue
  String status;

  OrderStatus(int code, String status) {
    this.code = code;
    this.status = status;
  }
}
