package com.jkys.consult.shine.request;

import com.jkys.consult.shine.util.Page;

/**
 * 医生收入请求参数类
 * @author: xiecw
 * @date: 2018/9/4
 */
public class DoctorIncomeRequest extends Page {
    private String date; //格式：201809

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
