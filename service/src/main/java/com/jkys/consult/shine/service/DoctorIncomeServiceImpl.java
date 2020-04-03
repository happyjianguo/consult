package com.jkys.consult.shine.service;

import com.jkys.consult.service.shine.DoctorIncomeService;
import com.jkys.consult.shine.bean.DoctorAdvisoryPrice;
import com.jkys.consult.shine.request.DoctorIncomeRequest;
import com.jkys.consult.shine.request.ModifyDoctorAdvisoryPriceRequest;
import com.jkys.consult.shine.response.DoctorIncomeResponse;
import com.jkys.consult.shine.response.EmptyResponse;
import com.jkys.consult.shine.response.QueryDoctorAdvisoryPriceResponse;
import com.jkys.consult.shine.response.SimpleResponse;
import com.jkys.consult.shine.utils.ContextUtil;
import com.jkys.consult.shine.utils.DateUtils;
import com.jkys.consult.shine.utils.ResponseServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xiecw
 * @date: 2018/9/4
 * @see com.jkys.shine.client.service.advisory.DoctorIncomeService
 */
@Service
public class DoctorIncomeServiceImpl implements DoctorIncomeService {

    private final Logger log = LoggerFactory.getLogger(DoctorIncomeServiceImpl.class);

    // TODO ---- 干掉 ------> todoByliming
//    @Autowired
//    private SystemSettingServcie systemSettingServcie;
    @Autowired
    private DoctorAdvisoryService doctorAdvisoryService;

    @Override
    public EmptyResponse queryDoctorIncome(DoctorIncomeRequest request) {
        Long doctorId = ContextUtil.getUserId();
        log.info("查询医生收入明细,user:{}",doctorId);
        String date = request.getDate();

        if (StringUtils.isBlank(date) || !StringUtils.isNumeric(date) || date.length() != 6) {
            return ResponseServiceUtils.invalidParams();
        }

        DoctorIncomeResponse doctorIncomeResponse = new DoctorIncomeResponse();
        String startDate = DateUtils.getFirstDayOfMonth(date);
        String endDate = DateUtils.getFirstDayOfNextMonth(date);

        return doctorAdvisoryService.queryIncomeList(request, doctorIncomeResponse, doctorId, startDate, endDate);
    }

    @Override
    public QueryDoctorAdvisoryPriceResponse queryDoctorAdvisoryPrice() {
        Long doctorId = ContextUtil.getUserId();
        log.info("查询医生咨询价格设置:{}",doctorId);
        return doctorAdvisoryService.getQueryDoctorAdvisoryPrice(doctorId);
    }

    @Override
    public SimpleResponse modifyDoctorAdvisoryPrice(ModifyDoctorAdvisoryPriceRequest request) {
        Long doctorId = ContextUtil.getUserId();
        Integer price = request.getPrice();
        log.info("修改医生咨询价格设置,{},{}",doctorId,price);
        // TODO ---- 设置医生默认价格 ------> todoByliming
//        DoctorAdvisoryPrice doctorAdvisoryPrice = systemSettingServcie.queryDoctorPrice();
        DoctorAdvisoryPrice doctorAdvisoryPrice = new DoctorAdvisoryPrice();

        if (price == null || price < doctorAdvisoryPrice.getMin() || price > doctorAdvisoryPrice.getMax()) {
            return ResponseServiceUtils.invalidParams();
        }
        return doctorAdvisoryService.modifyAdvisoryPrice(doctorId, price);
    }

}
