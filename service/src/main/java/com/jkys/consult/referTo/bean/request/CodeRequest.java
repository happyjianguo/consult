package com.jkys.consult.referTo.bean.request;

/**
 * @author gaobb
 */
public class CodeRequest extends BaseRequest {
    Integer uid;
    String mobile;
    int type;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
