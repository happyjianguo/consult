package com.jkys.consult.statemachine.enums;

public enum ConsultSubStatus {
  INIT(1, "咨询中初始状态"),
  MAY_CHANGE_DOCTOR(2, "可更换医生状态"),
  DO_NOT_CHANGE_DOCTOR(3, "可更换医生状态");

  int code;
  String name;

  ConsultSubStatus(int code, String name) {
    this.code = code;
    this.name = name;
  }
}
