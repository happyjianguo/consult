package com.jkys.consult.infrastructure.db.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据源
 */
public enum DataSourceType {
  //    SHARDING_DATA_SOURCE(-2,"sharding","sharding-jdbc数据源", Boolean.FALSE),
  JKYS(-1, "jkys", "jkys库", Boolean.FALSE),
  MEDICAL(0, "medical", "药品库", Boolean.FALSE);
//    CENTER(1, "center", "中央库", Boolean.TRUE),
//    COMPANY(2, "company", "公司库", Boolean.TRUE),
//    OTHER(3, "other", "外部数据源", Boolean.TRUE),
//    USER(4, "user", "测试数据源", Boolean.FALSE);

  @Getter
  @Setter
  private int code;
  @Getter
  @Setter
  private String source;
  @Getter
  @Setter
  private String name;
  @Getter
  @Setter
  private boolean drugSource;

  DataSourceType(Integer code, String source, String name, boolean drugSource) {
    this.code = code;
    this.source = source;
    this.name = name;
    this.drugSource = drugSource;
  }

  public static String getSourceByCode(Integer code) {
    for (DataSourceType type : DataSourceType.values()) {
      if (code == type.getCode()) {
        return type.getSource();
      }
    }
    return null;
  }

  public static int getCodeBySource(String value) {
    for (DataSourceType type : DataSourceType.values()) {
      if (value.equals(type.getSource())) {
        return type.getCode();
      }
    }
    return -1;
  }

  public static String getNameByCode(Integer code) {
    for (DataSourceType type : DataSourceType.values()) {
      if (code == type.getCode()) {
        return type.getName();
      }
    }
    return null;
  }

}
