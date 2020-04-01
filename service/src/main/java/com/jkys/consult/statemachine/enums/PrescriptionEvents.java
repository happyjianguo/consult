package com.jkys.consult.statemachine.enums;

public enum PrescriptionEvents {
  ACCEPT(1, "医生同意"),
  DISMISS(2, "医生拒绝"),
  APPROVE(3, "审核通过"),
  REJECT(4, "审核驳回"),
  USE(5, "使用"),
  EXPIRE(6, "过期");

  int code;
  String name;

  PrescriptionEvents(int code, String name) {
    this.code = code;
    this.name = name;
  }
}
