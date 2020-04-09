package com.jkys.consult.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: xiecw
 * @date: 2018/9/4
 */
// TODO ---- 响应内容，待定 ------> todoByliming
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Deprecated
public class DoctorPriceResponse {
    private Integer price; //价格：分
    private String effectiveRange; //医生自定价格的有效范围(分)，当前为0-20000，0为不收费
    private Boolean userDefined;//	医生是否修改了价格：true是，false否
    private Boolean verified;  //是否为验证医生：true是，false否
}
