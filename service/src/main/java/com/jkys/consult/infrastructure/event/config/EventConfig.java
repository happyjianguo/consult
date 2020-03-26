package com.jkys.consult.infrastructure.event.config;

import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {
  @Bean
  public EventBus configEvent() {
    EventBus eventBus = new EventBus();
    return eventBus;
  }
}
