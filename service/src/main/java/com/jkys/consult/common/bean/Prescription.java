package com.jkys.consult.common.bean;

import static com.jkys.consult.statemachine.enums.PrescriptionStatus.INIT;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jkys.consult.common.component.BaseEntity;
import com.jkys.consult.statemachine.enums.PrescriptionStatus;
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
@TableName(value = "t_prescription")
public class Prescription extends BaseEntity<Order> {

  private Long prescriptionId;


  /**
   * 当前订单状态
   */
  @Default
  private PrescriptionStatus status = INIT;

}
