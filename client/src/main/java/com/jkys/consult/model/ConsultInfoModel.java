package com.jkys.consult.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultInfoModel {

  /**
   * 咨询单号
   */
  private String consultId;

  /**
   * 咨询类型 2 专家门诊 3 开药门诊
   */
  private String consultType;

  /**
   * 下单时间
   */
  private String startTime;

  /**
   * 当前咨询状态,根据咨询单和订单获取
   */
  private String status;

  /**
   * 订单号
   */
  private String orderId;

  /**
   * 价格
  private String price;

  /**
   * 医生ID
   */
  private Long doctorId;

  /**
   * 医生姓名
   */
  private String doctorName;

  /**
   * 医生职称
   */
  private String title;

  /**
   * 医生科室
   */
  private String department;

  /**
   * 病人ID
   */
  private Long patientId;

  /**
   * 病人姓名
   */
  private String patientName;

  /**
   * 病人年龄
   */
  private String age;

  /**
   * 病人性别
   */
  private String gender;

  /**
   * 现病史描述
   */
  private Long presentIllness;

  /**
   * 既往史描述
   */
  private Long pastHistory;

  /**
   * 过敏史过描述
   */
  private Long allergyHistory;

}
