package com.jkys.consult.common.bean;

import com.jkys.consult.statemachine.enums.OrderEvents;
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
public class OrderDomainEvent {
    private Order order;
    private OrderEvents event;
}
