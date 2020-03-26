package com.jkys.consult.referTo.bean.response;

import java.io.Serializable;

/**
 * @author yangZh
 * @since 2018/8/7
 **/
public class PageResult<T> extends ListResult<T> implements Serializable {
    private static final long serialVersionUID = -4175732524560165341L;
    private int pageNo;
    private int pageSize;
    private int totalCount;

    public PageResult() {
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}