package com.jkys.consult.statemachine.enums;

import static com.jkys.consult.enums.ConsultModelStatus.CANCEL;
import static com.jkys.consult.enums.ConsultModelStatus.COMPLETE;
import static com.jkys.consult.enums.ConsultModelStatus.INPROCESS;
import static com.jkys.consult.enums.ConsultModelStatus.REFUND;
import static com.jkys.consult.enums.ConsultModelStatus.UNPAY;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.jkys.consult.enums.ConsultModelStatus;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public enum ConsultStatus {
  INIT(UNPAY,1, "初始"),
  WAIT_FOR_PROCESS(UNPAY, 2, "待咨询"),

  PROCESSING(INPROCESS, 3, "咨询中"),
  PROCESS_INIT(INPROCESS, 31, "咨询中初始状态"),
  MAY_CHANGE_DOCTOR(INPROCESS,32, "可更换医生状态"),
  STILL_WAIT(INPROCESS, 33, "继续等待"),

  CANCELED(CANCEL, 4, "已取消"),
  COMPLETED(COMPLETE, 5, "已完成"),
  TERMINATED(REFUND, 6, "已终止");


  @Getter
  private ConsultModelStatus type;
  @Getter
  int code;
  @Getter
  @EnumValue
  String status;

  ConsultStatus(ConsultModelStatus type, int code, String status) {
    this.type = type;
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
   * type-->statusEnum
   */
  public static List<ConsultStatus> getByType(ConsultModelStatus type) {
    List<ConsultStatus> consultStatusList = Arrays.asList(ConsultStatus.values())
        .parallelStream()
        .filter(statusEnum -> type.equals(statusEnum.getType()))
        .collect(Collectors.toList());
    if (!CollectionUtils.isEmpty(consultStatusList)) {
      return consultStatusList;
    }
    return null;
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
