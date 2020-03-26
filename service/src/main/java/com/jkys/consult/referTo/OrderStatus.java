package com.jkys.consult.referTo;

/**
 * 咨询订单支付状态
 */
public enum OrderStatus {
    INIT(0,"初始"),
    PAYING(1,"发起充值"),
    PAY_SUCCESS(2,"支付成功"),
    PAY_FAIL(3,"支付失败"),
    REFUND(4,"退款成功"),
    REFUND_FAIL(5,"退款失败");

    private Integer code;
    private String describe;

    OrderStatus(Integer code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }
}
