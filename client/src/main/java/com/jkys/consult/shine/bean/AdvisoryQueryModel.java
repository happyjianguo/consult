package com.jkys.consult.shine.bean;

import com.jkys.consult.shine.response.BaseResponse;
import java.util.Date;

/**
 * 咨询订单化
 *
 * @author yangZh
 * @since 2018/7/20
 */
public class AdvisoryQueryModel extends BaseResponse {
    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 医生姓名
     */
    private String doctorName;
    /**
     * 医生ID
     */
    private Long doctorId;

    /**
     * 医生手机号
     */

    private String doctorMobile;

    /**
     * 病人姓名
     */
    private String patientName;

    /**
     * 病人id
     */
    private Long patientId;

    /**
     * 病人手机号
     */
    private String patientMobile;

    /**
     * 咨询开始时间
     */
    private Date startTime;

    /**
     * 咨询结束时间
     */
    private Date endTime;

    /**
     * 咨询结束时间
     */
    private Date payTime;

    /**
     * 当前咨询状态
     */
    private Integer status;

    /**
     * 填写的病人性别
     */
    private Integer sex;

    /**
     * 填写的病人年龄
     */
    private Integer age;

    /**
     * 身高
     */
    private Integer height;

    /**
     * 体重
     */
    private Double weight;
    /**
     * 病情描述
     */
    private String describes;

    /**
     * 图片地址 使用;分割
     */
    private String imgUrl;

    /**
     * 是否是退款的 默认0   1 退款
     */
    private Integer refund;

    private String memo;

    /**
     * 既往史
     */
    private String pastHistory;

    /**
     * 现病史
     */
    private String presentIllness;

    /**
     * 过敏史
     */
    private String allergicHistory;

    /**
     * 过敏史
     */
    private Integer amount;

    /**
     * 过敏史
     */
    private Integer costCoin;

    /**
     * 过敏史
     */
    private Integer payAmount;

    /**
     * 等待医生第一次回复的时间
     */
    private Double waiting;

    private  String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getWaiting() {
        return waiting;
    }

    public void setWaiting(Double waiting) {
        this.waiting = waiting;
    }

    public String getMemo() {
        return memo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorMobile() {
        return doctorMobile;
    }

    public void setDoctorMobile(String doctorMobile) {
        this.doctorMobile = doctorMobile;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getPastHistory() {
        return pastHistory;
    }

    public void setPastHistory(String pastHistory) {
        this.pastHistory = pastHistory;
    }

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness;
    }

    public String getAllergicHistory() {
        return allergicHistory;
    }

    public void setAllergicHistory(String allergicHistory) {
        this.allergicHistory = allergicHistory;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCostCoin() {
        return costCoin;
    }

    public void setCostCoin(Integer costCoin) {
        this.costCoin = costCoin;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

}
