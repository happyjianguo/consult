package com.jkys.consult.infrastructure.mapstrut.demo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodType {
  private Long id;
  private String name;
  private int show;
  private int order;
}