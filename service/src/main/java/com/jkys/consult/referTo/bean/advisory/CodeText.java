package com.jkys.consult.referTo.bean.advisory;


import com.jkys.consult.referTo.bean.request.BaseRequest;

@Deprecated
public class CodeText extends BaseRequest {
    private String code;
    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
