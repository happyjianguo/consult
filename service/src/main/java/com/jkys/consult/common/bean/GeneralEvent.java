package com.jkys.consult.common.bean;

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
public class GeneralEvent<T, S> {
    private T object;
    private S message;
    private GeneralEventType event;
}
