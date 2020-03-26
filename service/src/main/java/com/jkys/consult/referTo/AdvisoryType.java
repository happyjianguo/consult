package com.jkys.consult.referTo;

public enum AdvisoryType {
    PATIENT(1, "病人付费咨询"),
    TEXT(2, "图文"),
    VIDEO(4, "视频");

    private int code;
    private String describe;


    AdvisoryType(Integer i, String s) {
        this.code = i;
        this.describe = s;
    }

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }
}
