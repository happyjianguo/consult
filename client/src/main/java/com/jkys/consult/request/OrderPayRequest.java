package com.jkys.consult.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPayRequest {

    // TODO ---- 测试用 ------> todoByliming
    private Boolean mock;

    /**
     * 支付单id
     */
    private String orderId;

    /**
     * 医生id
     */
//    private Long doctorId;

    /**
     * 病人id
     */
    private Long patientId;

    /**
     * 轮询调用
     */
    private Boolean reTry =false;

    /**
     * 调用来源  app ，wechat
     */
    private String client;

}
