package com.jkys.consult.referTo.bean.advisory;

/**
 * @author yangzh
 * @since 2019/5/28
 **/
public class StoreAdvisoryResult {
    /**
     * 是否可提交处方咨询申请 无正在咨询中的处方 true  else false
     */
    private Boolean consult = false;

    /**
     * 医生的咨询类型
     */
    @Deprecated //字段过期 @see advisoryType
    private Integer doctorAdvisoryType;

    /**
     * 咨询类型
     */
    private Integer advisoryType;

    private Long doctorId;
    /**
     * 咨询单信息
     */
    private StoreAdvisoryModel advisory;

    public Integer getAdvisoryType() {
        return advisoryType;
    }

    public void setAdvisoryType(Integer advisoryType) {
        this.advisoryType = advisoryType;
    }

    public Boolean getConsult() {
        return consult;
    }

    public void setConsult(Boolean consult) {
        this.consult = consult;
    }

    public StoreAdvisoryModel getAdvisory() {
        return advisory;
    }

    public void setAdvisory(StoreAdvisoryModel advisory) {
        this.advisory = advisory;
    }

    public Integer getDoctorAdvisoryType() {
        return doctorAdvisoryType;
    }

    public void setDoctorAdvisoryType(Integer doctorAdvisoryType) {
        this.doctorAdvisoryType = doctorAdvisoryType;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
