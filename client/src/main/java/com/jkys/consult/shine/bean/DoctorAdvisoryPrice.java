package com.jkys.consult.shine.bean;

/**
 * 医生咨询价格设置属性，单位分
 *
 * @author xiecw
 * @date 2018/9/5
 */
public class DoctorAdvisoryPrice {
    private Integer min;//有效价格最小值
    private Integer max; // 有效价格最大值
    private Integer defaultPrice; //如果没有设置的默认价格
    private String range; //有效价格范围：0-20000

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Integer defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
