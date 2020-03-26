package com.jkys.consult.shared;

public interface DomainEventPublisher {
    public void publish(Object event);

}
