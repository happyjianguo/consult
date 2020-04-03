package com.jkys.consult.shine.util;

/**
 * 公共分页类
 * @author: xiecw
 * @date: 2018/09/04
 */
public class Page {
    private Integer start = 1;            //页码，默认是第一页
    private Integer limit = 10;        //每页显示的记录数，默认是10
    private Integer totalRecord = 0;    //总记录数
    private Integer totalPage;            //总页数

    public Integer getOffSet() {
        return (getStart() - 1) * getLimit();
    }

    public Integer getStart() {
        return start == null || start < 1 ? 1 : start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit == null || limit < 1 ? 10 : limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
        //在设置总页数的时候计算出对应的总页数
        int totalPage = totalRecord % limit == 0 ? totalRecord / limit : totalRecord / limit + 1;
        this.setTotalPage(totalPage);
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    private void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
