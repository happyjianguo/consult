package com.jkys.consult.infrastructure.event.config;

import com.google.common.eventbus.AsyncEventBus;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {
//  @Bean
//  public EventBus configEvent() {
//    EventBus eventBus = new EventBus();
//    return eventBus;
//  }

  @Bean
  public AsyncEventBus asyncEventBus(){
    return new AsyncEventBus(Executors.newFixedThreadPool(10));
  }
}
