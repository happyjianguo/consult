package com.jkys.consult.shine.enums;

/**
 * 用户类型 0：管理员 1：医师 2：患者 3：家属 4：客服（护士） 5：内部医生  99：游客
 * 20: 线下渠道 21: 论坛圈子 22: 科室 23: 医院 24: 民间组织
 *
 * @author elvis.xu
 * @since 2015-6-11
 */
public enum UserTypeEnum {

    ADMIN((byte) 0, "管理员"),
    DOCTOR((byte) 1, "医生"),
    PATIENT((byte) 2, "患者"),
    FAMILY((byte) 3, "家属"),
    NURSE((byte) 4, "客服/护士"),
    INTERNAL_DOCTOR((byte) 5, "内部医生"),
    GUEST((byte) 99, "游客"),
    OFFLINE((byte) 20, "线下渠道"),
    QUANZI((byte) 21, "论坛圈子"),
    HOSPITAL_DEPARTMENT((byte) 22, "医院下的科室"),
    HOSPITAL((byte) 23, "医院"),
    ORG((byte) 24, "民间组织");

    private byte code;
    private String desc;

    // 构造方法
    UserTypeEnum(byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public byte getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String toString() {
        return UserTypeEnum.class.getSimpleName() + "[code=" + code + ", desc=" + desc + "]";
    }

    public static UserTypeEnum valueOfCode(Byte code) {
        if (code == null) {
            return null;
        }
        for (UserTypeEnum e : UserTypeEnum.values()) {
            if (e.getCode() == code.byteValue()) {
                return e;
            }
        }
        return null;
    }

    public static boolean validCode(Byte code) {
        return valueOfCode(code) != null;
    }

    public static boolean validCode(String code) {
        Byte val = null;
        try {
            val = Byte.valueOf(code);
        } catch (NumberFormatException e) {
            return false;
        }
        if (valueOfCode(val) == null) {
            return false;
        }
        return true;
    }

}
