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
public class DoctorInfoModel {

  /**
   * imageUrl : xxx
   * name : 张三
   * title : 主治医生
   * department : 外科
   * hospital : 人民医院
   * price : 100
   */

  private String imageUrl;
  private String name;
  private String title;
  private String department;
  private String hospital;
  private int price;
}
