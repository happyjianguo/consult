package com.jkys.consult.referTo.bean.advisory;


import com.jkys.consult.referTo.bean.request.BaseRequest;
import java.util.Date;

/**
 * 病人咨询信息
 *
 * @author yangZh
 * @since 2018/6/13
 **/
public class AdvisoryUserInfoModel extends BaseRequest {
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
     * 病情描述
     */
    private String describes;

    /**
     * 图片地址 使用;分割
     */
    private String[] imgUrl;

    /**
     * 咨询单号
     */
    private String orderNum;
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
     * 糖尿病类型
     */
    private CodeText diabetesType;

    private String diseaseCategory;

    /**
     * 价格
     */
    private Integer price;
    /**
     * 本次的糖币价格
     */
    private Integer costCoin;

    /**
     * 总金额：分
     */
    private Integer amount;

    private Long imGroupId;

    public Long getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(Long imGroupId) {
        this.imGroupId = imGroupId;
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

    public String[] getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String[] imgUrl) {
        this.imgUrl = imgUrl;
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

    public CodeText getDiabetesType() {
        return diabetesType;
    }

    public void setDiabetesType(CodeText diabetesType) {
        this.diabetesType = diabetesType;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDiseaseCategory() {
        return diseaseCategory;
    }

    public void setDiseaseCategory(String diseaseCategory) {
        this.diseaseCategory = diseaseCategory;
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

    public Integer getCostCoin() {
        return costCoin;
    }

    public void setCostCoin(Integer costCoin) {
        this.costCoin = costCoin;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
