package com.jkys.consult.model;

import java.util.List;
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
public class PrescriptionInfoModel {

  private String consultId;
  private String applicationFormId;
  private String prescriptionId;
  private String state;
  private List<PrescriptionDetail> detailList;

}
