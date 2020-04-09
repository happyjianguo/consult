package com.jkys.consult.common.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jkys.consult.common.component.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@TableName(value = "doctor_advisory_price")
public class DoctorConsultPrice extends BaseEntity<DoctorConsultPrice> {
  private Long doctorId;
  @TableField(value = "advisory_price")
  private Integer price;
}
