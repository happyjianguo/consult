package com.jkys.consult.request;

import com.jkys.phobos.exception.ServiceError;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 医生收入请求参数类
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class DoctorIncomeRequest {

  private Long doctorId;
  private String date; //格式：201809

  public DoctorIncomeRequest(Long doctorId, String date) {
    if (StringUtils.isEmpty(date)) {
      throw new ServiceError("0", "咨询月份不能为空");
    }
    if (!StringUtils.isNumeric(date) || date.length() != 6) {
      throw new ServiceError("0", "咨询月份格式为yyyyMM(例：202004)");
    }
    this.doctorId = doctorId;
    this.date = date;
  }
}
