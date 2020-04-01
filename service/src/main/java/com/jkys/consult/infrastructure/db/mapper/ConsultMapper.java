package com.jkys.consult.infrastructure.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jkys.consult.common.bean.Consult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ConsultMapper extends BaseMapper<Consult> {

}