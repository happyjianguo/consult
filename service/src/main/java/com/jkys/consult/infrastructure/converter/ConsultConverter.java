package com.jkys.consult.infrastructure.converter;

import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.model.ConsultInfoModel;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.beans.BeanUtils;

public class ConsultConverter extends BidirectionalConverter<Consult, ConsultInfoModel> {

  @Override
  public ConsultInfoModel convertTo(Consult consult, Type<ConsultInfoModel> type,
      MappingContext mappingContext) {
    ConsultInfoModel consultInfoModel = new ConsultInfoModel();
    BeanUtils.copyProperties(consult, consultInfoModel);
    return consultInfoModel;
  }

  @Override
  public Consult convertFrom(ConsultInfoModel consultInfoModel, Type<Consult> type,
      MappingContext mappingContext) {
    return null;
  }
}