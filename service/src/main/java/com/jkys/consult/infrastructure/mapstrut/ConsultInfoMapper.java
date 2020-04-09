package com.jkys.consult.infrastructure.mapstrut;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.Order;
import com.jkys.consult.model.ConsultInfoModel;
import com.jkys.consult.reponse.ConsultInfoResponse;
import com.jkys.usercenter.client.domain.user.BaseUserInfo;
import com.jkys.usercenter.client.domain.user.UserExtraInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { consultStateFormat.class})
//@Mapper
public interface ConsultInfoMapper {
//  ConsultInfoMapper MAPPER = Mappers.getMapper(ConsultInfoMapper.class);
  @Mappings({
      @Mapping(source = "consult.consultId",target = "consultId"),
      @Mapping(source = "consult.consultType",target = "consultType"),
      @Mapping(source = "consult.startTime",target = "startTime"),
      @Mapping(target = "status", expression = "java(consult.getStatus().getType().getName())"),
      @Mapping(source = "order.orderId",target = "orderId"),
      @Mapping(source = "order.price",target = "price"),
      @Mapping(source = "consult.doctorId",target = "doctorId"),
      @Mapping(source = "doctor.username",target = "doctorName"),
      @Mapping(source = "doctor.avatar",target = "doctorAvatar"),
      @Mapping(target = "title", expression = "java(doctorExtra.getPairs().get(\"title\").toString())"),
      @Mapping(target = "department", expression = "java(doctorExtra.getPairs().get(\"department\").toString())"),
      @Mapping(target = "hospital", expression = "java(doctorExtra.getPairs().get(\"hospital\").toString())"),
      @Mapping(source = "consult.patientId",target = "patientId"),
      @Mapping(source = "patient.username",target = "patientName"),
      @Mapping(source = "patient.avatar",target = "patientAvatar"),
      @Mapping(source = "patient.sex",target = "gender"),
      @Mapping(target = "age", expression = "java(Integer.valueOf(patientExtra.getPairs().get(\"age\").toString()))"),
      @Mapping(target = "presentIllness", expression = "java(patientExtra.getPairs().get(\"presentIllness\").toString())"),
      @Mapping(target = "pastHistory", expression = "java(patientExtra.getPairs().get(\"pastHistory\").toString())"),
      @Mapping(target = "allergyHistory", expression = "java(patientExtra.getPairs().get(\"allergyHistory\").toString())")
  })
  ConsultInfoModel toConsultInfoModel(Consult consult, Order order, BaseUserInfo doctor, UserExtraInfo doctorExtra, BaseUserInfo patient, UserExtraInfo patientExtra);

  @Mappings({
      @Mapping(source = "doctorId",target = "doctorId"),
      @Mapping(source = "patientId",target = "patientId"),
      @Mapping(source = "consultType",target = "consultType"),
      @Mapping(source = "status",target = "consultState")
//      @Mapping(target = "consultState", expression = "java(consultState.getStatus())")
  })
  ConsultInfoResponse toConsultInfoResponse(Consult consult);
}