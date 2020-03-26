package com.jkys.consult.referTo.bean.advisory;

/**
 * @author yangZh
 * @since 2018/7/24
 **/
public class HistoryDescModel {

    private Long id;
    /**
     * 病情描述
     */
    private String describes;

    /**
     * 既往史
     */
    private String pastHistory;

    /**
     * 现病史
     */
    private String presentIllness;

    /**
     * 过敏史
     */
    private String allergicHistory;

    /**
     * 咨询开始时间。即完成信息录入时间
     */
    private Long startTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getPastHistory() {
        return pastHistory;
    }

    public void setPastHistory(String pastHistory) {
        this.pastHistory = pastHistory;
    }

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness;
    }

    public String getAllergicHistory() {
        return allergicHistory;
    }

    public void setAllergicHistory(String allergicHistory) {
        this.allergicHistory = allergicHistory;
    }

}
