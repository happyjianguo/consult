package com.jkys.consult.infrastructure.mapstrut.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodInfo {
  private Long id;
  private String title;
  private double price;
  private int order;
  private Long typeId;
}