package com.jkys.consult.referTo.bean.advisory;

import com.jkys.phobos.annotation.MillisecondTimestamp;
import java.util.Date;

/**
 * @author yangzh
 * @since 2019/5/28
 **/
public class StoreAdvisoryModel {
    private Long id;
    /**
     * 医生id
     */
    private Long doctorId;

    /**
     * 店员id
     */
    private Long storeUserId;

    /**
     * 咨询开始时间
     */
    @MillisecondTimestamp
    private Date startTime;

    /**
     * 咨询结束时间
     */
    @MillisecondTimestamp
    private Date endTime;

    /**
     * 当前咨询状态
     */
    private Integer status;

    /**
     *  店员姓名
     */
    private String storeUserName;

    private Long imGroupId;

    public Long getImGroupId() {
        return imGroupId;
    }

    public void setImGroupId(Long imGroupId) {
        this.imGroupId = imGroupId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(Long storeUserId) {
        this.storeUserId = storeUserId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStoreUserName() {
        return storeUserName;
    }

    public void setStoreUserName(String storeUserName) {
        this.storeUserName = storeUserName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
