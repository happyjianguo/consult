package com.jkys.consult.referTo.bean;

import java.time.LocalDateTime;

/**
 * @author yangZh
 * @since 2018/8/31
 **/
public class OrderInfoDO {
    /**
     * ID
     */
    private Long id;
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
    private LocalDateTime startTime;
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
     * 消费金额
     */
    private Integer amount;
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
     * 退款状态
     */
    private Integer refund;
    /**
     * 消费类型 免费FREE 自费 PAY
     */
    private String type;
    /**
     * 用户头像
     */
    private String avatar;

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
