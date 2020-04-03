package com.jkys.consult.shine.mapper;

import com.jkys.consult.shine.bean.SystemSettingModel;
import org.apache.ibatis.annotations.Select;

/**
 * @author xiecw
 * @date 2018/9/5
 */
public interface SystemSettingMapper {
    @Select("select name, value from system_setting where code = #{code}")
    SystemSettingModel queryByCode(String code);
}