package com.jkys.consult.shine.service;

import com.jkys.common.kafkaUtil.bean.BizEnum;
import com.jkys.common.kafkaUtil.bean.Msg;
import com.jkys.common.simplemsg.util.SimpleMsgSender;
import com.jkys.common.simplemsg.util.constant.TopicEnum;
import com.jkys.consult.common.bean.PatientAdvisoryInfo;
import com.jkys.consult.shine.enums.OrderType;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${consult.consultFinishTag}")
    private String consultFinishTag;
//    @Value("${shine.advisoryStatusChangeTag}")
//    private String advisoryStatusChangeTag;

    @Autowired
    @Qualifier("finishSender")
    private SimpleMsgSender<Msg> finishSender;
//    @Autowired
//    @Qualifier("shine-advisoryStatusChange")
//    private SimpleMsgSender<Msg> advisoryStatusChangeSender;

    /**
     * 咨询结束消息事件
     *
     * @param info 咨询信息
     */
    public void sendFinishMsg(PatientAdvisoryInfo info) {
        sendFinishMsg(info,false);
    }

    public void sendFinishMsg(PatientAdvisoryInfo info,boolean isDoctor) {
        if (info == null || !info.getOrderNum().contains(OrderType.PAY.name())) {
            return;
        }
        Msg msg = new Msg();
        Map<String, String> map = new HashMap<>();
        map.put("infoId", String.valueOf(info.getId()));//咨询订单id
        map.put("isDoctor",isDoctor?"1":"0");
        map.put("source",info.getSource());
        msg.setForBiz(BizEnum.SHINE_USER_ADVISORY_FINISH);
        msg.setArgs(map);
        try {
            finishSender.sendTagMsg(TopicEnum.JKYS_BUSINESS, consultFinishTag, msg);
        } catch (Exception e) {
            log.error("sendIhMsg error", e);
        }
    }


//    /**
//     *开始咨询和结束咨询
//     */
//    public void medicsaleAdvisoryStatusChange(PatientAdvisoryInfo info, Date time) {
//        Msg msg = new Msg();
//        Map<String, String> map = new HashMap<>();
//        map.put("infoId", String.valueOf(info.getId()));//咨询订单id
//        map.put("doctorId",String.valueOf(info.getDoctorId()));
//        map.put("patientId",String.valueOf(info.getPatientId()));
//        map.put("status",String.valueOf(info.getStatus()));
//        map.put("time", DateUtils.formatDate(time));
//        msg.setArgs(map);
//        try {
//            advisoryStatusChangeSender.sendTagMsg(TopicEnum.JKYS_BUSINESS,advisoryStatusChangeTag, msg);
//        } catch (Exception e) {
//            log.error("sendIhMsg error", e);
//        }
//    }

}
