package com.jkys.consult.common.bean;

import com.jkys.consult.statemachine.enums.ConsultEvents;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultDomainEvent {
    private Consult consult;
    private Order order;
    private ConsultEvents event;
}
