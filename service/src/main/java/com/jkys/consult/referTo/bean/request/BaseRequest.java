package com.jkys.consult.referTo.bean.request;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author yangZh
 * @since 2018/6/13
 **/
public class BaseRequest implements Serializable {
    private Long id;

    private Date gmtCreate;

    private Date gmtModify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        if (id != null) {
            this.id = id;
        }

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

    private static final ToStringStyle TO_STRING_STYLE = new ToStringStyle() {
        @Override
        protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
            if (value instanceof Date) {
                value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(value);
            }
            super.appendDetail(buffer, fieldName, value);
        }
    };

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, TO_STRING_STYLE);
    }

}
