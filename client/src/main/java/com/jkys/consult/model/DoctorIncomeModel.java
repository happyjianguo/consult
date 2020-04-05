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
public class DoctorIncomeModel {

  private String title;
  private Integer price;
  private String time;
  private Long id;
  private Long doctorId;
  private Long patientId;

}
