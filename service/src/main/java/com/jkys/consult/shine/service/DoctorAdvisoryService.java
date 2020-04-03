package com.jkys.consult.shine.service;

import com.jkys.consult.shine.bean.DoctorAdvisoryPrice;
import com.jkys.consult.shine.bean.DoctorIncomeModel;
import com.jkys.consult.shine.bean.PatientAdvisoryOrder;
import com.jkys.consult.shine.enums.OrderType;
import com.jkys.consult.shine.mapper.DoctorAdvisoryPriceMapper;
import com.jkys.consult.shine.mapper.PatientAdvisoryOrderMapper;
import com.jkys.consult.shine.request.DoctorIncomeRequest;
import com.jkys.consult.shine.response.DoctorIncomeResponse;
import com.jkys.consult.shine.response.EmptyResponse;
import com.jkys.consult.shine.response.QueryDoctorAdvisoryPriceResponse;
import com.jkys.consult.shine.response.SimpleResponse;
import com.jkys.consult.shine.utils.DateUtils;
import com.jkys.consult.shine.utils.ResponseServiceUtils;
import com.jkys.usercenter.client.domain.user.BaseUserInfo;
import com.jkys.usercenter.client.uenum.UserStatusEnum;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiecw
 * @date 2018/9/7
 */
@Service
public class DoctorAdvisoryService {
    private static final Logger log = LoggerFactory.getLogger(DoctorAdvisoryService.class);

    @Autowired
    private DoctorAdvisoryPriceMapper doctorAdvisoryPriceMapper;
    @Autowired
    private SystemSettingServcie systemSettingServcie;
    @Autowired
    private PatientAdvisoryOrderMapper patientAdvisoryOrderMapper;
    @Autowired
    private UserCenterUserService userCenterUserService;

    // 根据医生ID查询咨询服务价格
    public QueryDoctorAdvisoryPriceResponse getQueryDoctorAdvisoryPrice(Long doctorId) {
        Integer price = doctorAdvisoryPriceMapper.queryDoctorAdvisoryPrice(doctorId);
        DoctorAdvisoryPrice doctorAdvisoryPrice = systemSettingServcie.queryDoctorPrice();

        QueryDoctorAdvisoryPriceResponse response = new QueryDoctorAdvisoryPriceResponse();
        // 没有设置咨询价格
        if (price == null) {
            response.setPrice(doctorAdvisoryPrice.getDefaultPrice());
            response.setUserDefined(false);
        } else {
            response.setPrice(price);
            response.setUserDefined(true);
        }
        log.info("getQueryDoctorAdvisoryPrice：服务价格：{},{}",doctorId,response.getPrice());
        // 是否为验证医生
        BaseUserInfo userInfo = userCenterUserService.findUserById(doctorId);
        response.setVerified(userInfo != null && userInfo.getStatus() != null && userInfo.getStatus() ==
                UserStatusEnum.EFFECTIVE.getValue());

        response.setEffectiveRange(doctorAdvisoryPrice.getRange());
        return response;
    }

    /**
     * 修改咨询价格
     */
    public SimpleResponse modifyAdvisoryPrice(Long doctorId, Integer price) {
        // 判断记录是否存在，不存在加一条
        Integer beforePrice = doctorAdvisoryPriceMapper.queryDoctorAdvisoryPrice(doctorId);
        if (beforePrice == null) {
            Integer cnt = doctorAdvisoryPriceMapper.addAdvisoryPrice(doctorId, price);
            if (cnt != 1) {
                return ResponseServiceUtils.failure();
            }
        } else {
            Integer count = doctorAdvisoryPriceMapper.modifyAdvisoryPrice(doctorId, price);
            if (count != 1) {
                return ResponseServiceUtils.failure();
            }
        }
        return ResponseServiceUtils.success();
    }

    /**
     * 根据月份分页查询收入列表
     */
    public EmptyResponse queryIncomeList(DoctorIncomeRequest request, DoctorIncomeResponse doctorIncomeResponse, Long
            doctorId, String startDate, String endDate) {
        List<PatientAdvisoryOrder> doctorIncome = patientAdvisoryOrderMapper.queryDoctorIncome(doctorId, startDate, endDate, request);

        if(doctorIncome == null || doctorIncome.size() == 0){
            return doctorIncomeResponse;
        }

        Integer total = patientAdvisoryOrderMapper.queryDoctorTotalIncome(doctorId, startDate, endDate);

        // 转换输出
        List<DoctorIncomeModel> incomeDetail = new ArrayList<>(doctorIncome.size());
        for (PatientAdvisoryOrder advisoryOrder : doctorIncome) {
            DoctorIncomeModel doctorIncomeModel = new DoctorIncomeModel();
            doctorIncomeModel.setTime(DateUtils.formatDate(advisoryOrder.getGmtCreate(), DateUtils.MMdd_SPLIT));
            doctorIncomeModel.setPrice(advisoryOrder.getAmount());
            doctorIncomeModel.setId(advisoryOrder.getId());
            doctorIncomeModel.setPatientId(advisoryOrder.getPatientId());
            doctorIncomeModel.setDoctorId(advisoryOrder.getDoctorId());
            // 如果退款价格为负数,价格为负数
            // TODO ---- 退款逻辑，重写 ------> todoByliming
            if (OrderType.BACK.name().equals(advisoryOrder.getType())) {
                doctorIncomeModel.setPrice(-doctorIncomeModel.getPrice());
            }
            doctorIncomeModel.setTitle("图文咨询 " + advisoryOrder.getBizCode());
            // 添加doctor_id patient_id 跳转到详情页面
            incomeDetail.add(doctorIncomeModel);
        }
        doctorIncomeResponse.setTotal(total);
        doctorIncomeResponse.setIncomeDetail(incomeDetail);
        return doctorIncomeResponse;
    }
}
