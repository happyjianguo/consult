package com.jkys.consult.referTo.bean.response;

import com.jkys.consult.referTo.bean.advisory.DoctorIncomeModel;
import java.util.List;

/**
 * @author: xiecw
 * @date: 2018/9/4
 */
public class DoctorIncomeResponse extends EmptyResponse {
    private Integer total; //当前时间的总收入
    private List<DoctorIncomeModel> incomeDetail; //收入明细

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<DoctorIncomeModel> getIncomeDetail() {
        return incomeDetail;
    }

    public void setIncomeDetail(List<DoctorIncomeModel> incomeDetail) {
        this.incomeDetail = incomeDetail;
    }
}
