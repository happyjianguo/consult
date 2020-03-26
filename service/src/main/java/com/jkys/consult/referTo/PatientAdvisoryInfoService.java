package com.jkys.consult.referTo;

import com.jkys.consult.referTo.bean.advisory.AdvisoryQuery;
import com.jkys.consult.referTo.bean.advisory.AdvisoryQueryModel;
import com.jkys.consult.referTo.bean.advisory.AdvisoryUserInfoModel;
import com.jkys.consult.referTo.bean.advisory.ConsultModel;
import com.jkys.consult.referTo.bean.advisory.HistoryDescModel;
import com.jkys.consult.referTo.bean.request.AdvisoryRequest;
import com.jkys.consult.referTo.bean.response.BaseResponse;
import com.jkys.consult.referTo.bean.response.PageResult;
import com.jkys.phobos.annotation.Name;
import com.jkys.phobos.annotation.Service;
import java.util.List;

/**
 * 用户咨询单查询更新服务
 * @author yangZh
 * @since 2018/7/23
 **/
@Service("jkys-shine.PatientAdvisoryInfoService:1.0.0")
public interface PatientAdvisoryInfoService {

    PageResult<AdvisoryQueryModel> queryUserInfoList(AdvisoryQuery query);

    /**
     * 更新
     * @param model
     * @return
     */
    Boolean updateUserInfo(AdvisoryUserInfoModel model);

    /**
     * 完善病人信息
     * @param req 信息
     * @return 更新后的病人信息
     */
    BaseResponse finishInfo(AdvisoryUserInfoModel req);

    /**
     * 查询最新一条的咨询记录,返回当前的可咨询情况
     * @param request 使用 医生id 病人id
     * @return obj
     */
    ConsultModel findLastOrderStatus(AdvisoryRequest request);

    /**
     * 按id查询 病人咨询记录
     * @param id id 主键
     * @return obj
     */
    AdvisoryUserInfoModel findOne(@Name("id") Long id);

    /**
     * 查询历史病史
     * @param uid 用户id
     * @return list
     */
    List<HistoryDescModel> describeList(@Name("uid") Long uid);

    /**
     * 查询最新的咨询单
     * @param doctorId 医生ID
     * @param patientId 病人ID
     * @param status 咨询单状态,正常的状态1-5， -1：全部  -2 咨询过的有效咨询（2-4）
     * @return model
     */
    ConsultModel queryLastInfo(@Name("doctorId") Long doctorId, @Name("patientId") Long patientId,
        @Name("status") Integer status);

    /**
     * 查询咨询中的医生ID
     * @param patientId
     * @return 咨询中的医生ID
     */
    List<Long> queryOnAdvisory(Long patientId);
}