package com.jkys.consult.statemachine.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum ConsultStatus {
  INIT(1, "待咨询"),
  WAIT_FOR_PROCESS(2, "待咨询"),

  PROCESSING(3, "咨询中"),
  PROCESS_INIT(31, "咨询中初始状态"),
  MAY_CHANGE_DOCTOR(32, "可更换医生状态"),
  STILL_WAIT(33, "继续等待"),

  CANCELED(4, "已取消"),
  COMPLETED(5, "已完成"),
  TERMINATED(6, "已终止");

  @Getter
  int code;
  @Getter
  @EnumValue
  String status;

  ConsultStatus(int code, String status) {
    this.code = code;
    this.status = status;
  }

  /**
   * status是否合法
   */
  public static boolean isIn(String status) {
    return Arrays.asList(ConsultStatus.values()).parallelStream().
        anyMatch(value -> StringUtils.equals(value.getStatus(), status));
  }

  /**
   * 判断status是否合法
   */
  public static boolean isIn(String status, ConsultStatus... statusEnums) {
    return Arrays.asList(statusEnums).parallelStream().
        anyMatch(value -> StringUtils.equals(value.getStatus(), status));
  }

  /**
   * 判断status是否相等
   */
  public static boolean equals(String status, ConsultStatus statusEnum) {
    return StringUtils.equalsIgnoreCase(status, statusEnum.getStatus());

  }

  /**
   * status-->statusEnum
   */
  public static ConsultStatus getByStatus(String status) {
    Optional<ConsultStatus> statusEnumOptional = Arrays.asList(ConsultStatus.values())
        .parallelStream()
        .filter(statusEnum -> StringUtils.equalsIgnoreCase(status, statusEnum.getStatus()))
        .findAny();
    if (statusEnumOptional.isPresent()) {
      return statusEnumOptional.get();
    }
    return null;
  }

  /**
   * 判断是否订单已终结，取消、关闭、成功、拒绝都属于终结状态
   */
  public static boolean isFinish(String status) {
    return isIn(status, TERMINATED, COMPLETED);
  }

  /**
   * 判断订单是否是初始创建状态 对于： WAIT_REAL_NAME_AUTH, WAIT_BORROW 都可能是初始状态 对于其他：暂时为CREATE状态
   */
  public static boolean isInitialStatus(String status) {
    return isIn(status, INIT);
  }
}
