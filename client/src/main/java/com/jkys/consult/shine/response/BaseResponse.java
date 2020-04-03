package com.jkys.consult.shine.response;

import java.io.Serializable;


public class BaseResponse implements Serializable {

    public static String successMsg = "成功";
    public static String failMsg = "失败";

    private Long id;

    private Boolean success;

    private String message;

    private Integer errorCode;

    public BaseResponse() {
    }

    public BaseResponse(String m, Boolean s) {
        this.message = m;
        this.success = s;
    }

    public void autoMsg(String msg, boolean result) {
        this.message = msg;
        this.success = result;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.success = false;
        this.errorCode = errorCode;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
