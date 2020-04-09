package com.jkys.consult.infrastructure.mapstrut.demo;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

//@Mapper(componentModel = "spring")
@Mapper
public interface GoodInfoMapper {
  GoodInfoMapper MAPPER = Mappers.getMapper(GoodInfoMapper.class);
  @Mappings({
      @Mapping(source = "type",target = "goodType"),
      @Mapping(source = "good",target = "goodInfo"),
      @Mapping(source = "type.name",target = "typeName"),
      @Mapping(source = "good.id",target = "goodId"),
      @Mapping(source = "good.title",target = "goodName"),
      @Mapping(source = "good.price",target = "goodPrice")
  })
  GoodInfoDTO toGoodInfoDTO(GoodInfo good, GoodType type);

  @Mappings({
      @Mapping(source = "goodInfo.id",target = "id"),
      @Mapping(source = "goodInfo.title",target = "title"),
      @Mapping(source = "goodInfo.price",target = "price")
//      @Mapping(source = "goodId",target = "id"),
//      @Mapping(source = "goodName",target = "title"),
//      @Mapping(source = "goodPrice",target = "price")
  })
  GoodInfo infoFromGoodInfoDTO(GoodInfoDTO dto);

  @Mappings({
      @Mapping(source = "typeName",target = "name"),
  })
  GoodType typeFromGoodInfoDTO(GoodInfoDTO dto);

}