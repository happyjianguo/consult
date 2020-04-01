package com.jkys.consult.common.bean;

import com.jkys.consult.statemachine.enums.PrescriptionEvents;
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
public class PrescriptionDomainEvent {
    private Prescription prescription;
    private PrescriptionEvents event;
}
