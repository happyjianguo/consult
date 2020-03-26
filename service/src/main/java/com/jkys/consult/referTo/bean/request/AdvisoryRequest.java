package com.jkys.consult.referTo.bean.request;

/**
 * @author yangZh
 * @since 2018/7/11
 **/
public class AdvisoryRequest {
    /**
     * 咨询单id
     */
    private Long id;
    /**
     * 支付单id
     */
    private Long orderId;

    /**
     * 医生id
     */
    private Long doctorId;
    /**
     * 更换的新医生id
     */
    private Long newDoctorId;
    /**
     * 病人id
     */
    private Long patientId;

    private Long imGroupId;
    /**
     * 轮询调用
     */
    private Boolean reTry =false;

    private Double version=0d;
    /**
     * 更换医生方式 默认0：覆盖更换，1：客户端录入信息更换
     */
    private Integer changeType;

    /**
     * 调用来源  app ，wechat
     */
    private String client;

    public Long getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(Long imGroupId) {
        this.imGroupId = imGroupId;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public Long getNewDoctorId() {
        return newDoctorId;
    }

    public void setNewDoctorId(Long newDoctorId) {
        this.newDoctorId = newDoctorId;
    }

    public Boolean getReTry() {
        return reTry;
    }

    public void setReTry(Boolean reTry) {
        this.reTry = reTry;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
