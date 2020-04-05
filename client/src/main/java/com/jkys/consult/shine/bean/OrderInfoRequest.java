package com.jkys.consult.shine.bean;

import com.jkys.consult.shine.util.Page;

/**
 * @author ks
 * @since 2018/8/31
 **/
public class OrderInfoRequest extends Page {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 查询日期
     */
    private String date;
    /**
     * 查询的订单状态
     */
    private Integer status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getOffset() {
        return (getStart() - 1) * getLimit();
    }
}
