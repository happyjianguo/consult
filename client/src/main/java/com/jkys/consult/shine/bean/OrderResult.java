package com.jkys.consult.shine.bean;

import com.jkys.consult.model.OrderInfoModel;
import java.util.List;
import java.util.Map;

/**
 * @author yangZh
 * @since 2018/12/4
 **/
public class OrderResult {

    private Map<String,Integer> sumMap;
    private List<OrderInfoModel> orderList;

    public Map<String, Integer> getSumMap() {
        return sumMap;
    }

    public void setSumMap(Map<String, Integer> sumMap) {
        this.sumMap = sumMap;
    }

    public List<OrderInfoModel> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderInfoModel> orderList) {
        this.orderList = orderList;
    }
}
