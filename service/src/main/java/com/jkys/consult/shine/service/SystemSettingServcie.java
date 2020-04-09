package com.jkys.consult.shine.service;

import com.jkys.consult.shine.bean.DoctorAdvisoryPrice;
import com.jkys.consult.shine.bean.SystemSettingModel;
import com.jkys.consult.common.constants.Constants;
import com.jkys.consult.shine.mapper.SystemSettingMapper;
import com.jkys.consult.shine.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiecw
 * @date 2018/9/5
 */
@Service
public class SystemSettingServcie {

    @Autowired
    private SystemSettingMapper systemSettingMapper;
    @Autowired
    private RedisService redisService;
    private final static int TIME_OUT = 60 * 60 * 24 * 15;

    public DoctorAdvisoryPrice queryDoctorPrice() {
        String value;
        value = redisService.get(Constants.DOCTOR_ADVISORY_PRICE);
        if (StringUtils.isEmpty(value)) {
            SystemSettingModel systemSettingModel = systemSettingMapper.queryByCode(Constants.DOCTOR_ADVISORY_PRICE);
            if (systemSettingModel == null) {
                return null;
            }
            value = systemSettingModel.getValue();
            redisService.set(Constants.DOCTOR_ADVISORY_PRICE, value, TIME_OUT);
        }
        return JsonUtil.GsonToBean(value, DoctorAdvisoryPrice.class);
    }
}
