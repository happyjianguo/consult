package com.jkys.consult.common.bean;

import static com.jkys.consult.statemachine.enums.ConsultStatus.INIT;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jkys.consult.common.BaseEntity;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "t_consult")
public class Consult extends BaseEntity<Consult> {

  /**
   * 咨询单号
   */
  //TODO 等同于patient_advisory_order的biz_code
  private String consultId;

  /**
   * 当前咨询状态
   */
  @Default
  private ConsultStatus status = INIT;

  /**
   * 医生id
   */
  private Long doctorId;

  /**
   * 病人id
   */
  private Long patientId;

  /**
   * 咨询类型 2 专家门诊 3 开药门诊
   */
  @TableField(value = "advisory_type")
  private Integer consultType;

//  private LocalDateTime start_time;
//  private LocalDateTime end_time;
//  private double waiting;

}
