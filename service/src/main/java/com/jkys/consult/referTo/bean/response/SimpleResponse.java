package com.jkys.consult.referTo.bean.response;

/**
 * 简单的应答
 *
 * @author xiecw
 * @date 2018/9/4
 */
public class SimpleResponse extends EmptyResponse {
    private Boolean success;
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
