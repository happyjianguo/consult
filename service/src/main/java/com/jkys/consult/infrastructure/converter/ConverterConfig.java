package com.jkys.consult.infrastructure.converter;

import com.google.gson.Gson;
import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.statemachine.enums.ConsultStatus;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

  @Bean
  public MapperFactory mapperFactory(){
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    ConverterFactory converterFactory = mapperFactory.getConverterFactory();
    converterFactory.registerConverter("consultConverter", consultConverter());
    return mapperFactory;
  }

  @Bean
  public ConsultConverter consultConverter(){
    return new ConsultConverter();
  }

  public static void main(String[] args) {
    MapperFactory consultMapperFactory = new DefaultMapperFactory.Builder().build();
    consultMapperFactory.classMap(Consult.class, ConsultInfoModel.class)
        .field("consultId", "consultId")
        .field("status", "status")
        .field("doctorId", "doctorId")
        .field("patientId", "patientId")
        .field("consultType", "consultType")
        .byDefault()
        .register();
    MapperFacade mapper = consultMapperFactory.getMapperFacade();
    Consult consult = Consult.builder()
        .consultId("WZ00001")
        .consultType(2)
        .doctorId(1L)
        .patientId(2L)
        .status(ConsultStatus.WAIT_FOR_PROCESS)
        .build();
    ConsultInfoModel consultInfoModel = mapper.map(consult, ConsultInfoModel.class);
    Gson gson = new Gson();
    System.out.println(gson.toJson(consultInfoModel));
  }
}
