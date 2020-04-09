package com.jkys.consult.reponse;

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
public class ConsultInfoResponse {

  private Long doctorId;
  private Long patientId;
  private Integer consultType;
  private Integer consultState;
  private String consultId;
}
