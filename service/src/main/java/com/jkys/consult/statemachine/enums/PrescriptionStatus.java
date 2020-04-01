package com.jkys.consult.statemachine.enums;

public enum PrescriptionStatus {
  INIT(0, "初始"),
  APPLICATION(1, "已申请"),
  DISMISSED(2, "被拒绝"),
  OPENING(3, "已开具"),
  REJECTED(4, "审核拒绝"),
  EXPIRED(5, "已过期"),
  ALREADY_USED(6, "已使用"),
  UNUSED(7, "待使用");

  int code;
  String name;

  PrescriptionStatus(int code, String name) {
    this.code = code;
    this.name = name;
  }
}
