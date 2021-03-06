package com.jkys.consult.common.bean;

import static com.jkys.consult.statemachine.enums.ConsultStatus.INIT;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jkys.consult.common.component.BaseEntity;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import java.time.LocalDateTime;
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
   * 咨询类型 1 专家门诊 2 开药门诊
   */
  @TableField(value = "advisory_type")
  private Integer consultType;

  /**
   * 商城订单ID，咨询单终了时发消息用
   */
  private String mallOrderId;

  private LocalDateTime startTime;

  private String client;

//  private LocalDateTime end_time;
//  private double waiting;

}
