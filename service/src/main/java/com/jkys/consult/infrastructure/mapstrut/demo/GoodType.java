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
public class GoodType {
  private Long id;
  private String name;
  private int show;
  private int order;
}