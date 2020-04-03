package com.jkys.consult.shine.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 特殊医生处理：不管是否绑定关系，只要发给其中的一个医生，就创建群组
 *
 * @author xiecw
 * @date 2018/12/13
 */
@Component
public class SpecialDoctorHandler {

    //主任医生
    private static Long majorDoctorId;
    private static List<Long> allDoctor;
    // 医助
    private static List<Long> helperList = null;

    @Value("${specialMajorDoctorId}")
    private Long major;
    @Value("${specialhelperDoctorIds}")
    private String helperDoctorIds;
    private static String openSpecial;

    @Value("${specialOpenSpecial}")
    public void setOpenSpecial(String openSpecial) {
        SpecialDoctorHandler.openSpecial = openSpecial;
    }

    @PostConstruct
    private void post() {
        majorDoctorId = major;

        String[] split = helperDoctorIds.split(",");

        allDoctor = new ArrayList<Long>(split.length + 1);
        helperList = new ArrayList<Long>();
        for (String helper : split) {
            helperList.add(Long.valueOf(helper.trim()));
        }
        allDoctor.addAll(helperList);
        allDoctor.add(majorDoctorId);
    }

    /** 是否特殊处理的医生 */
    public static boolean isSpecialDoctor(Long doctorId) {
        if(!"true".equals(openSpecial)){
            return false;
        }
        if (majorDoctorId.equals(doctorId)) {
            return true;
        }
        for (Long helper : helperList) {
            if (helper.equals(doctorId)) {
                return true;
            }
        }

        return false;
    }

    /** 获取专家组的所有成员 */
    public static List<Long> allSpecialDoctor(){
        return allDoctor;
    }

}
