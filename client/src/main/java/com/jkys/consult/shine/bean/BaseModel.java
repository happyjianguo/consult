package com.jkys.consult.shine.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gaobb
 */
public class BaseModel implements Serializable {
    protected Long id;
    protected Date gmtCreate;
    protected Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }


}
