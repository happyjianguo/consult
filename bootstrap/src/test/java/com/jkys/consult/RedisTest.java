package com.jkys.consult;

import com.google.common.base.Stopwatch;
import com.jkys.common.lock.impl.Redis;
import com.jkys.consult.base.BaseTest;
import com.jkys.consult.infrastructure.rpc.chat.ChatMessageService;
import com.jkys.consult.shine.service.MqServer;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

@Slf4j
public class RedisTest extends BaseTest {

  @Autowired
  private ChatMessageService chatMessageService;

  @Autowired
  private JedisPool pool;

  @Resource
  private MqServer mqServer;

  private static Stopwatch stopwatch;

  @BeforeEach
  void setUp() {
    stopwatch = Stopwatch.createStarted();
  }

  @AfterAll
  static void afterAll() {
    log.error(stopwatch.toString());
  }

  @Test
  public void send(){
    mqServer.sendFinishMsg(null);
  }

  @Test
  public void testRedis(){
    Redis redis = new Redis();
    redis.setJedisPool(pool);
    boolean r = redis.tryLock();
    System.out.println("ddddddd");
  }

  @Test
  public void queryLastMessageDate() {
    chatMessageService.queryLastMessageTimeStamp(doctorId, patientId);
    System.out.println("ddddddd");
  }

}