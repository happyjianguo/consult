package com.jkys.consult.infrastructure.event.comsumer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.ConsultDomainEvent;
import com.jkys.consult.common.bean.GeneralEvent;
import com.jkys.consult.common.bean.GeneralEventType;
import com.jkys.consult.infrastructure.rpc.chat.ChatMessageService;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.logic.ConsultStateLogic;
import com.jkys.consult.service.ConsultService;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.im.client.enumeration.MessageType;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultDomainListener {

//    @Autowired
//    private DemoService demoService;

    @Autowired
    private EventBus eventBus;

    @PostConstruct
    public void init(){
        eventBus.register(this);
    }

    @Autowired
    ConsultStateLogic consultStateLogic;

    @Autowired
    ConsultLogic consultLogic;

    @Autowired
    ConsultService consultService;

    @Autowired
    private ChatMessageService chatMessageService;

    @SneakyThrows
    @Subscribe
    public void handleEvent(ConsultDomainEvent domainEvent) {
        ConsultEvents event = domainEvent.getEvent();
        Consult consult = domainEvent.getConsult();

        consultStateLogic.handleAction(event, consult);
    }

    @SneakyThrows
    @Subscribe
    public void handleEvent(GeneralEvent event) {
        if(event.getEvent().equals(GeneralEventType.SEND)){
            Consult consult = (Consult) event.getObject();
            Consult info = consultService.selectByConsultId(consult.getConsultId());
            String finishText = event.getMessage().toString();
//            Constants.FINISH_TEXT2

            // TODO ---- 消息中包含24小时推荐医生链接 ------> todoByliming
            chatMessageService.sendFinishAdvisoryMessage(info.getDoctorId(), info.getPatientId(),
                finishText, MessageType.Finish.name());

        }
    }

//    @Subscribe
//    public void createConsult(ConsultDomainEvent event) {
//        // invoke application service or domain service
//        System.out.println("ConsultListener: createConsult......");
//    }
//
//    @Subscribe
//    public void changeConsult(ConsultDomainEvent event) {
//        // invoke application service or domain service
//        System.out.println("ConsultListener: changeConsult......");
//    }
}
