package com.jkys.consult.statemachine.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum OrderStatus {
  INIT(1, "初始"),

  WAIT_FOR_PAY(2, "待支付"),

  CANCELED(3, "已取消"),

  PAYING(4,"发起充值"),
  PAYED(5, "已支付"),
//  PAY_FAIL(6,"支付失败"),

  REFUNDED(7, "已退款");
//  REFUND_FAIL(8,"退款失败");


  @Getter
  int code;
  @Getter
  @EnumValue
  String status;

  OrderStatus(int code, String status) {
    this.code = code;
    this.status = status;
  }

  /**
   * status-->statusEnum
   */
  public static OrderStatus getByStatus(String status) {
    Optional<OrderStatus> statusEnumOptional = Arrays.asList(OrderStatus.values())
        .parallelStream()
        .filter(statusEnum -> StringUtils.equalsIgnoreCase(status, statusEnum.getStatus()))
        .findAny();
    if (statusEnumOptional.isPresent()) {
      return statusEnumOptional.get();
    }
    return null;
  }

  /**
   * code-->statusEnum
   */
  public static OrderStatus getByCode(int code) {
    Optional<OrderStatus> statusEnumOptional = Arrays.asList(OrderStatus.values())
        .parallelStream()
        .filter(statusEnum -> code == statusEnum.getCode())
        .findAny();
    if (statusEnumOptional.isPresent()) {
      return statusEnumOptional.get();
    }
    return null;
  }

}
