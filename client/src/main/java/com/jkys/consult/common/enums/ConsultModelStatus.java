package com.jkys.consult.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum ConsultModelStatus {
  UNPAY(1, "待支付"),
  INPROCESS(2, "咨询中"),
  CANCEL(3, "已取消"),
  COMPLETE(4, "已完成"),
  REFUND(5, "已退款");

  @Getter
  int code;
  @Getter
  @EnumValue
  String name;

  ConsultModelStatus(int code, String name) {
    this.code = code;
    this.name = name;
  }

  /**
   * code-->statusEnum
   */
  public static ConsultModelStatus getByCode(int code) {
    Optional<ConsultModelStatus> statusEnumOptional = Arrays.asList(ConsultModelStatus.values())
        .parallelStream()
        .filter(statusEnum -> code == statusEnum.getCode())
        .findAny();
    if (statusEnumOptional.isPresent()) {
      return statusEnumOptional.get();
    }
    return null;
  }

  /**
   * status-->statusEnum
   */
  public static ConsultModelStatus getByStatus(String status) {
    Optional<ConsultModelStatus> statusEnumOptional = Arrays.asList(ConsultModelStatus.values())
        .parallelStream()
        .filter(statusEnum -> StringUtils.equalsIgnoreCase(status, statusEnum.getName()))
        .findAny();
    if (statusEnumOptional.isPresent()) {
      return statusEnumOptional.get();
    }
    return null;
  }

}
