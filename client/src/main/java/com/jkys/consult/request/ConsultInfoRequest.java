package com.jkys.consult.request;

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
public class ConsultInfoRequest {
  private Long patientId;
  private Long doctorId;
  private Integer consultType;
  private String consultState;
  private String mallOrderId;
  private String client;
}
