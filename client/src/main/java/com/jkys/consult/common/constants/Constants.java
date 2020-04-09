package com.jkys.consult.common.constants;

/**
 * 常量类
 *
 * @author yangZh
 * @since 2018/6/23
 **/
public interface Constants {
    Integer ZERO = 0;
    Integer RETRY_TIMES_4_PAY = 3;

    // 支付云币余结果 1 成功 0 失败 2余额不足
    Integer COIN_FAIL = 0;
    Integer COIN_SUCCESS = 1;
    Integer COIN_INSUFFICIENT = 2;

    // TODO ---- 专家问诊和开药问诊和原来的咨询类型1 正常付费咨询 2 图文 4视频什么关系 ------> todoByliming
    Integer EXPERT_CONSULT_TYPE = 1;
    Integer PRESCRIBE_CONSULT_TYPE = 2;

    String DOCTOR_TYPE = "doctor";
    String PATIENT_TYPE = "patient";

    
    //咨询的最大有效时间
    Long EXPIRE_TIME = 48 * 60 * 60 * 1000L + 100L;
    //指定的无回复时间
    Long NOREPEAT_TIME = 12 * 60 * 60 * 1000L;

    String FINISH_TEXT = "1、医生已对此次疾病问题给出医学建议，咨询结束。\n2、若咨询已达48小时，系统会自动结束本次咨询";
    String FINISH_TEXT_24 = "1、医生已对此次疾病问题给出医学建议，咨询结束。\n2、若咨询已达24小时，系统会自动结束本次咨询";
    String AUTO_TEXT = "医生因临床工作繁忙，暂无法回复本次咨询，本次咨询已由系统自动结束；云币已退还到您的云币账户中。";
    String FINISH_TEXT2 = "因用户主动取消咨询，系统已自动结束咨询订单。";
    String BEGIN_TEXT = "# " +
            "问诊开始\n1、咨询期间可针对此次疾病问题发送不限条消息\n2、医生对此次疾病问题给出医学建议后，本次咨询将会结束\n3、若咨询期内医生没有回复过咨询，系统将会在48小时候后退还云币\n4、晚上11点至次日上午7点该时间段为医生非服务时间，医生可能无法及时回复您的咨询";
    String IM_FINISH = "Finish";
    String IM_RICHLINK = "RichLink";
    String IM_SYSTEM = "SysText";

    //短信消息
//    String DOCTOR_SIGIN_SMS = "智云医生";
    //短信模板
//    String DOCTOR_MESSAGE_SMS = "SMS_142148199";

    /** System_Setting表的code */
    String DOCTOR_ADVISORY_PRICE = "DOCTOR_ADVISORY_PRICE";

    /** 参数验证失败文案提醒 */
    String PARAM_INVALID_MSG = "参数错误";

    /** 处理成功文案 */
    String SUCCESS_MSG = "成功";
    /** 处理失败文案 */
    String FAILURE_MSG = "失败";

}
