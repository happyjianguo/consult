package com.jkys.consult.shine.response;

/**
 * @author: xiecw
 * @date: 2018/9/4
 */
public class QueryDoctorAdvisoryPriceResponse {
    private Integer price; //价格：分
    private String effectiveRange; //医生自定价格的有效范围(分)，当前为0-20000，0为不收费
    private Boolean userDefined;//	医生是否修改了价格：true是，false否
    private Boolean verified;  //是否为验证医生：true是，false否

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getEffectiveRange() {
        return effectiveRange;
    }

    public void setEffectiveRange(String effectiveRange) {
        this.effectiveRange = effectiveRange;
    }

    public Boolean getUserDefined() {
        return userDefined;
    }

    public void setUserDefined(Boolean userDefined) {
        this.userDefined = userDefined;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}
