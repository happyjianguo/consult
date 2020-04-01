package com.jkys.consult.statemachine.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public enum ConsultEvents {
  CREATE(0, "创建咨询单"),
  START(1, "开启咨询"),
  CANCEL(2, "取消咨询"),
  COMPLETE(3, "完成咨询"),
  TERMINATE(4, "终止咨询"),
  CHANGE_POSSIBILITY(5, "确定是否更换医生条件状态"),
  CHANGE_DOCTOR(6, "用户确认更换医生"),
  WAIT(7, "继续等待");

  @Getter
  int code;
  @Getter
  String event;

  ConsultEvents(int code, String event) {
    this.code = code;
    this.event = event;
  }

  /**
   * 判断
   * @param eventName
   * @return
   */
  public static ConsultEvents getEvent(String eventName) {
    if (StringUtils.isBlank(eventName)) {
      return null;
    }

    Optional<ConsultEvents> resultOptional = Arrays.asList(ConsultEvents.values()).parallelStream().filter(eventEnum ->
        StringUtils.equals(eventName, eventEnum.getEvent())).findAny();

    if (resultOptional.isPresent()) {
      return resultOptional.get();
    }
    return null;
  }

  /**
   * 判断event是否相等
   */
  public static boolean equals(String status, ConsultStatus statusEnum) {
    return StringUtils.equalsIgnoreCase(status, statusEnum.getStatus());

  }

}
