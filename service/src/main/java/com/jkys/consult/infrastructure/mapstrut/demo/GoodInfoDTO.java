package com.jkys.consult.infrastructure.mapstrut.demo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoodInfoDTO {
  private GoodInfo goodInfo;
  private GoodType goodType;

  private String goodId;
  private String goodName;
  private double goodPrice;
  private String typeName;

  public static void main(String[] args) {
    //查询商品基本信息
    GoodInfo goodInfoBean = GoodInfo.builder()
        .id(1L)
        .order(1111)
        .price(2.00d)
        .title("标题")
        .typeId(2L)
        .build();

    //查询商品类型基本信息
    GoodType typeBean = GoodType.builder()
        .id(1L)
        .name("名称")
        .order(1111)
        .show(123)
        .build();
    GoodInfoDTO goodInfoDTO = GoodInfoMapper.MAPPER.toGoodInfoDTO(goodInfoBean, typeBean);
    System.out.println(goodInfoDTO);

    GoodInfo goodInfoBean1 = GoodInfoMapper.MAPPER.infoFromGoodInfoDTO(goodInfoDTO);
    System.out.println(goodInfoBean1);

//    GoodType typeBean1 = GoodInfoMapper.MAPPER.typeFromGoodInfoDTO(goodInfoDTO);
//    System.out.println(typeBean1);

  }
}