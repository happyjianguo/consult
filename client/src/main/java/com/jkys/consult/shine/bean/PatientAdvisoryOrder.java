package com.jkys.consult.shine.bean;

/**
 * 病人咨询订单
 *
 * @author yangZh
 * @since 2018/7/13
 **/
public class PatientAdvisoryOrder extends BaseModel {
    /**
     * 医生id
     */
    private Long doctorId;

    /**
     * 病人id
     */
    private Long patientId;

    /**
     * 当前订单状态 详见OrderStatus
     */
    private Integer status;

    /**
     * 咨询订单号
     */
    private String orderNum;

    /**
     * 本次的糖币价格
     */
    private Integer costCoin;

    /**
     * 订单充值的糖币
     */
    private Long chargeCoin;

    /**
     * 订单类型  OrderType
     */
    private String type;

    /**
     * 业务编号 type+orderNum
     */
    private String bizCode;

    private Integer amount;//总金额：分
    /**
     * 支付串信息
     */
    private String payString;


    public String getPayString() {
        return payString;
    }

    public void setPayString(String payString) {
        this.payString = payString;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCostCoin() {
        return costCoin;
    }

    public void setCostCoin(Integer costCoin) {
        this.costCoin = costCoin;
    }

    public Long getChargeCoin() {
        return chargeCoin;
    }

    public void setChargeCoin(Long chargeCoin) {
        this.chargeCoin = chargeCoin;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
