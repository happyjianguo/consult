package com.jkys.consult.infrastructure.mq;

import com.jkys.common.kafkaUtil.KafkaSender;
import com.jkys.common.simplemsg.util.SimpleMsgSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleMsgSenderConfig {

  @Value("${kafka.broker.list}")
  private String kafkaBrokerList;

  @Bean
  public KafkaSender kafkaSender(){
    KafkaSender sender = new KafkaSender();
    sender.setBrokerListStr(kafkaBrokerList);
    return sender;
  }

  @Bean(name = "finishSender")
  public SimpleMsgSender simpleMsgSender(){
    SimpleMsgSender sender = new SimpleMsgSender();
    sender.setKafkaSender(kafkaSender());
    return sender;
  }
}
