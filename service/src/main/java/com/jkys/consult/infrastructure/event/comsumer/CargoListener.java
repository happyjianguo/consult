package com.jkys.consult.infrastructure.event.comsumer;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jkys.consult.common.model.UserBookDomainEvent;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CargoListener {
    
//    @Autowired
//    private DemoService demoService;

    @Autowired
    private EventBus eventBus;
    
    @PostConstruct
    public void init(){
        eventBus.register(this);
    }

    @Subscribe
    public void recordCargoBook(UserBookDomainEvent event) {
        // invoke application service or domain service
        System.out.println("CargoListener: recordCargoBook......");
    }
}
