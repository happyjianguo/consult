package com.jkys.consult.statemachine.utils;

import com.jkys.consult.statemachine.constant.Constants;
import com.jkys.consult.statemachine.enums.ConsultEvents;
import com.jkys.consult.statemachine.enums.OrderEvents;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class MessageUtil {
  public static Message<ConsultEvents> getMessage(ConsultEvents event, String bizcode){
    Message<ConsultEvents> eventMsg = MessageBuilder.withPayload(event)
        // key 与 status change 时不同，对应的model也不同
        .setHeader(Constants.BIZ_CODE, bizcode)
        .build();
    return eventMsg;
  }

  public static Message<OrderEvents> getMessage(OrderEvents event, String bizcode){
    Message<OrderEvents> eventMsg = MessageBuilder.withPayload(event)
        // key 与 status change 时不同，对应的model也不同
        .setHeader(Constants.BIZ_CODE, bizcode)
        .build();
    return eventMsg;
  }
}
