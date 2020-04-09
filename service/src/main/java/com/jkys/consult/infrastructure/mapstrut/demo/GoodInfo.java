package com.jkys.consult.infrastructure.mapstrut.demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodInfo {
  private Long id;
  private String title;
  private double price;
  private int order;
  private Long typeId;
}