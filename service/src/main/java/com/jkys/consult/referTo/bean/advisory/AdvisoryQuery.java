package com.jkys.consult.referTo.bean.advisory;

import com.jkys.consult.referTo.bean.request.BaseRequest;
import java.util.Date;
import java.util.List;

/**
 * 咨询订单化查询入参
 *
 * @author yangZh
 * @since 2018/7/20
 */
public class AdvisoryQuery extends BaseRequest {
    /**
     * 咨询订单编号
     */
    private String orderNum;

    /**
     * 医生ID
     */
    private List<Long> doctorIds;

    /**
     * 病人id
     */
    private  List<Long> patientIds;

    private Long patientId;

    private Long doctorId;
    /**
     * 咨询开始时间（）
     */
    private Date startDate;

    /**
     * 咨询开始时间
     */
    private Date endDate;
    /**
     * 咨询开始时间（）
     */
    private Date payStart;

    /**
     * 咨询开始时间
     */
    private Date payEnd;


    /**
     * 状态
     */
    private Integer status;
    /**
     * 退款 1 是
     */
    private Integer refund;

    /**
     * 咨询方式 1免费 2付费 0全部
     */
    private Integer aType;

    /**
     * 24小时
     */
    private int hour24;

    /**
     * 8小时
     */
    private int hour8;

    private Long imGroupId;

    private int pageNo = 1;
    private int pageSize = 10;

    public Long getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(Long imGroupId) {
        this.imGroupId = imGroupId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public int getHour24() {
        return hour24;
    }

    public void setHour24(int hour24) {
        this.hour24 = hour24;
    }

    public int getHour8() {
        return hour8;
    }

    public void setHour8(int hour8) {
        this.hour8 = hour8;
    }

    public Integer getaType() {
        return aType;
    }

    public void setaType(Integer aType) {
        this.aType = aType;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public List<Long> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<Long> doctorIds) {
        this.doctorIds = doctorIds;
    }

    public List<Long> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(List<Long> patientIds) {
        this.patientIds = patientIds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Date getPayStart() {
        return payStart;
    }

    public void setPayStart(Date payStart) {
        this.payStart = payStart;
    }

    public Date getPayEnd() {
        return payEnd;
    }

    public void setPayEnd(Date payEnd) {
        this.payEnd = payEnd;
    }
}
