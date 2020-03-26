package com.jkys.consult.referTo.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangZh
 * @since 2018/8/7
 **/
public class ListResult<T> implements Serializable {
    private static final long serialVersionUID = 3957675638859096351L;
    private List<T> data;

    public ListResult() {
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static <T> ListResult<T> success(List<T> data) {
        ListResult<T> result = new ListResult();
        result.setData(data);
        return result;
    }
}