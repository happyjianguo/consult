package com.jkys.consult.common.bean;

import com.jkys.consult.common.BaseEntity;
import java.util.Date;

/**
 * 病人咨询信息
 *
 * @author yangZh
 * @since 2018/7/13
 **/
public class PatientAdvisoryInfo extends BaseEntity {
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
    private Date startTime;

    /**
     * 咨询结束时间
     */
    private Date endTime;

    /**
     * 预计到期时间
     */
    private Long expireTime;
    /**
     * 当前咨询状态
     */
    private Integer status;

    /**
     * 填写的病人姓名
     */
    private String patientName;

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
     * 支付订单号
     */
    private String orderNum;

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
     * 区域编码
     */
    private String districtCode;
    /**
     * 国家
     */
    private String country;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区/县
     */
    private String county;
    /**
     * 身份证号
     */
    private String card;
    /**
     * 疾病类型（大类）
     */
    private String diseaseCategory;
    /**
     * 糖尿病类型
     */
    private Integer diabetesType;

    /**
     * 等待医生第一次回复的时间
     */
    private Double waiting;
    /**
     * 医生回复状态 1已回复
     */
    private Integer repeatStatus;

    /**
     * 通过更换医生生成的订单，更换前的咨询单id
     */
    private Long beforeInfoId;

    /**
     * 咨询单来源 STORE, IN_APP
     */
    private String source;


    private int advisoryType;

    private Long imGroupId;

    public Long getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(Long imGroupId) {
        this.imGroupId = imGroupId;
    }

    public int getAdvisoryType() {
        return advisoryType;
    }

    public void setAdvisoryType(int advisoryType) {
        this.advisoryType = advisoryType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getBeforeInfoId() {
        return beforeInfoId;
    }

    public void setBeforeInfoId(Long beforeInfoId) {
        this.beforeInfoId = beforeInfoId;
    }

    public Integer getRepeatStatus() {
        return repeatStatus;
    }

    public void setRepeatStatus(Integer repeatStatus) {
        this.repeatStatus = repeatStatus;
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

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
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


    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getDiseaseCategory() {
        return diseaseCategory;
    }

    public void setDiseaseCategory(String diseaseCategory) {
        this.diseaseCategory = diseaseCategory;
    }

    public Integer getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(Integer diabetesType) {
        this.diabetesType = diabetesType;
    }
}
