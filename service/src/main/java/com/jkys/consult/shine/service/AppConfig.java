package com.jkys.consult.shine.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by JetslyLi on 2015/4/14.
 */
@Component
@Getter
@Setter
public class AppConfig {
//    private final  static LocalDateTime LIMIT_TIME = LocalDateTime.of(2018, 9, 30, 23, 59, 59);

    @Value("${app.profiles}")
    private String profiles;

    /**
     * 咨询云币价格
     */
    @Value("${advisory.cost.coin}")
    private Integer costCoin = 0;

    /**
     * 咨询糖币原价
     */
    @Value("${advisory.original.coin}")
    private Integer originalCoin = 0;

//    /**
//     * 控糖顾问id
//     */
//    @Value("${inside.doctor}")
//    private Long insideDoctor = 0L;

//    /**
//     * 客服id
//     */
//    @Value("${customer}")
//    private Long customer = 0L;

//    /**
//     * 会员用户专属客服ID
//     */
//    @Value("${vipuser.customerServiceId}")
//    private Long vipCustomerServiceId = 0L;
//
//    /**
//     * 会员用户专属健康顾问ID
//     */
//    @Value("${vipuser.health.consultant}")
//    private Long vipConsultant = 0L;
//
//    /**
//     * 免费抗击疫情问诊
//     */
//    @Value("${shine.freeDoctor}")
//    private Long freeDoctor = 0L;
//
//    /**
//     * im
//     */
//    @Value("${chatserver.address}")
//    private String chatRequestBaseURL;

    @Value("${h5.domain}")
    private String h5Domain;

    /**
     * 是否为生产环境
     */
    public boolean isProduction() {
        return this.profiles.equals("production");
    }

    public boolean isPre() {
        return this.profiles.equals("pre");
    }

    /**
     * 是否为测试环境
     */
    public boolean isQa() {
        return this.profiles.equals("qa");
    }

    /**
     * 是否为开发环境
     */
    public boolean isDevelop() {
        return this.profiles.equals("develop");
    }
}
