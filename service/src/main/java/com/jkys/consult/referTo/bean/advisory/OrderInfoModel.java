package com.jkys.consult.referTo.bean.advisory;


import com.jkys.consult.referTo.bean.response.BaseResponse;

/**
 * @author yangZh
 * @since 2018/8/31
 **/
public class OrderInfoModel extends BaseResponse {
    /**
     * 医生ID
     */
    private Long doctorId;
    /**
     * 病人ID
     */
    private Long patientId;
    /**
     * 下单时间
     */
    private String startTime;
    /**
     * 病人姓名
     */
    private String patientName;
    /**
     * 医生姓名
     */
    private String doctorName;
    /**
     * 病情描述
     */
    private String describes;
    /**
     * 咨询金额
     */
    private Integer amount;

    /**
     * 消费云币额
     */
    @Deprecated
    private Integer costCoin;

    /**
     * 状态
     */
    private Integer status;
    /**
     * 状态名称
     */
    private String statusMsg;
    /**
     * 医院名称
     */
    private String hospital;
    /**
     * 消费类型 免费FREE 自费 PAY
     */
    @Deprecated
    private String type;
    /**
     * 用户头像
     */
    private String avatar;

    @Deprecated
    private String bizCode;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCostCoin() {
        return costCoin;
    }

    public void setCostCoin(Integer costCoin) {
        this.costCoin = costCoin;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }
}
