package com.jkys.consult.infrastructure.event.comsumer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jkys.consult.common.bean.Consult;
import com.jkys.consult.common.bean.ConsultDomainEvent;
import com.jkys.consult.logic.ConsultLogic;
import com.jkys.consult.logic.ConsultStateLogic;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import javax.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsultListener {

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

    @SneakyThrows
    @Subscribe
    public void handleEvent(ConsultDomainEvent domainEvent) {
        ConsultEvents event = domainEvent.getEvent();
        Consult consult = domainEvent.getConsult();
        String consultId = consult.getConsultId();

        consultStateLogic.handleAction(event, consultId);
    }

    @Subscribe
    public void createConsult(ConsultDomainEvent event) {
        // invoke application service or domain service
        System.out.println("ConsultListener: createConsult......");
    }

    @Subscribe
    public void changeConsult(ConsultDomainEvent event) {
        // invoke application service or domain service
        System.out.println("ConsultListener: changeConsult......");
    }
}
