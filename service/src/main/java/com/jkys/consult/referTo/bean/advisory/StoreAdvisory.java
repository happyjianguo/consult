package com.jkys.consult.referTo.bean.advisory;

/**
 * @author yangzh
 * @since 2019/5/28
 **/
public class StoreAdvisory {
    /**
     * 药店店员id--uc中的用户id
     */
    private Long storeUserId;
    /**
     * 咨询的客服医生id
     */
    private Long doctorId;

    /**
     * 咨询的客服医生id
     */
    private Integer advisoryType;

    /**
     * 咨询对应的im  group
     */
    private Long imGroupId;

    public Long getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(Long imGroupId) {
        this.imGroupId = imGroupId;
    }

    public Integer getAdvisoryType() {
        return advisoryType;
    }

    public void setAdvisoryType(Integer advisoryType) {
        this.advisoryType = advisoryType;
    }

    public Long getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(Long storeUserId) {
        this.storeUserId = storeUserId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

}
