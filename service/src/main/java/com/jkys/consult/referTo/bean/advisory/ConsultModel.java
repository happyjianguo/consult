package com.jkys.consult.referTo.bean.advisory;

import com.jkys.consult.referTo.bean.response.BaseResponse;

/**
 * @author yangZh
 * @since 2018/7/10
 **/
public class ConsultModel extends BaseResponse {

    /**
     * 医生id
     */
    private Long doctorId;

    /**
     * 病人id
     */
    private Long patientId;


    /**
     * 咨询开始时间
     */
//    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Long startTime;

    /**
     * 咨询结束时间
     */
//    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Long endTime;

    /**
     * 是否已支付 true
     */
    private Boolean pay  = false;

    /**
     * 是否可咨询 true 客服和控糖顾问可直接咨询
     */
    private Boolean consult = false;

    /**
     * 是否已完成信息录入 true
     */
    private Boolean finishUserInfo  = false;

    /**
     * 咨询中
     */
    private Boolean talking  = false;

    /**
     * 咨询云币价格
     */
    private Integer coin;

    /**
     * 原咨询云币价格
     */
    private Integer originalCoin;

    /**
     * 咨询单状态
     */
    private  Integer status;

    /**
     * 指定时间内医生是否已回复
     */
    private  Boolean noRepeat =false;

    public Boolean getNoRepeat() {
        return noRepeat;
    }

    public void setNoRepeat(Boolean noRepeat) {
        this.noRepeat = noRepeat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }


    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Boolean getPay() {
        return pay;
    }

    public void setPay(Boolean pay) {
        this.pay = pay;
    }

    public Boolean getConsult() {
        return consult;
    }

    public void setConsult(Boolean consult) {
        this.consult = consult;
    }

    public Boolean getFinishUserInfo() {
        return finishUserInfo;
    }

    public void setFinishUserInfo(Boolean finishUserInfo) {
        this.finishUserInfo = finishUserInfo;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getOriginalCoin() {
        return originalCoin;
    }

    public void setOriginalCoin(Integer originalCoin) {
        this.originalCoin = originalCoin;
    }

    public Boolean getTalking() {
        return talking;
    }

    public void setTalking(Boolean talking) {
        this.talking = talking;
    }
}
