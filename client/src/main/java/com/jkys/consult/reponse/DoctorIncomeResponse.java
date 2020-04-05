package com.jkys.consult.reponse;

import com.jkys.consult.model.DoctorIncomeModel;
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
public class DoctorIncomeResponse {

  private Integer total; //当前时间的总收入
  private List<DoctorIncomeModel> incomeDetails; //收入明细
}
