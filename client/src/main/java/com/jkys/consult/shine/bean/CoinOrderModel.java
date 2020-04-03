package com.jkys.consult.shine.bean;

import com.jkys.consult.shine.response.BaseResponse;

/**
 * 咨询糖币支付信息
 * @author yangZh
 * @since 2018/7/13
 **/

public class CoinOrderModel extends BaseResponse {

    /**
     * 云币余额
     */
    private Integer coin;

    /**
     * 医生定价
     */
    private Integer price;

    /**
     * 原云币价格
     */
    private Integer originalCoin;

    /**
     * 支付订单号
     */
    private String bizCode;

    /**
     * 支付信息串
     */
    private String payString;
    /**
     * 当前咨询单id
     */
    private Long infoId;
    /**
     * 当前支付单id
     */
    private Long orderId;
    /**
     * 当前咨询单咨询价格
     */
    private Integer amount;
    /**
     * 本次支付云币余结果 1 成功 0 失败 2余额不足
     */
    private Integer coinPay =0;

    /**
     * 为了兼容消息发送是否是服务端还是前端发送，如果服务端已发送，告诉前端
     */
    private Boolean hasSent =false;

    public Integer getCoinPay() {
        return coinPay;
    }

    public void setCoinPay(Integer coinPay) {
        this.coinPay = coinPay;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getPayString() {
        return payString;
    }

    public void setPayString(String payString) {
        this.payString = payString;
    }

    public Integer getOriginalCoin() {
        return originalCoin;
    }

    public void setOriginalCoin(Integer originalCoin) {
        this.originalCoin = originalCoin;
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Boolean getHasSent() {
        return hasSent;
    }

    public void setHasSent(Boolean hasSent) {
        this.hasSent = hasSent;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
