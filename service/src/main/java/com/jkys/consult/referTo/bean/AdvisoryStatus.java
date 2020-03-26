package com.jkys.consult.referTo.bean;

import java.util.HashMap;
import java.util.Map;

public enum AdvisoryStatus implements BaseCodeEnum {
    INIT(1, "初始"), BEGIN(2, "咨询中"), OVER(3, "已结束"), REFUND(4, "已退款"), CANCEL(5, "已取消");

    private Integer code;
    private String describe;

    private static Map<Integer, AdvisoryStatus> enumMap = new HashMap<>();

    static {
        for (AdvisoryStatus type : AdvisoryStatus.values()) {
            enumMap.put(type.getCode(), type);
        }
    }

    AdvisoryStatus(Integer i, String s) {
        this.code = i;
        this.describe = s;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    public static AdvisoryStatus getEnum(Integer value) {
        return enumMap.get(value);
    }
}
