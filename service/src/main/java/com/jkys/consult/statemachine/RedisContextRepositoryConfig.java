package com.jkys.consult.statemachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;

@Configuration
public class RedisContextRepositoryConfig<S, E> {

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;

  @Bean("redisStateMachineContextRepository")
  public RedisStateMachineContextRepository<S, E> redisStateMachineContextRepository(){
    RedisStateMachineContextRepository<S, E> repository = new RedisStateMachineContextRepository<>(
        redisConnectionFactory);
    return repository;
  }

//  @Bean("redisStateMachineContextRepository")
//  public RedisStateMachineContextRepository redisStateMachineContextRepository(){
//    RedisStateMachineContextRepository<ConsultStatus, ConsultEvents> repository = new RedisStateMachineContextRepository<>(
//        redisConnectionFactory);
//    return repository;
//  }

}
