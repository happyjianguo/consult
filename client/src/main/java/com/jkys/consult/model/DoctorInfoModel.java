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

  /**
   * 医生图片
   */
  private String imageUrl;

  /**
   * 医生姓名
   */
  private String doctorName;

  /**
   * 医生职称
   */
  private String title;

  /**
   * 医生科室
   */
  private String department;

  /**
   * 医生医院
   */
  private String hospital;

  /**
   * 医生咨询价格
   */
  private int price;
}
