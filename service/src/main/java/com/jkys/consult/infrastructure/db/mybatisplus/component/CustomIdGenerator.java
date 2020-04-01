package com.jkys.consult.infrastructure.db.mybatisplus.component;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CustomIdGenerator implements IdentifierGenerator {

  @Autowired
  private CustomSequenceGenerator sequenceGenerator;

  private final AtomicLong al = new AtomicLong(1);

  private final static Object lock = "lock";

  @Override
  public Number nextId(Object entity) {
    //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
    String bizKey = entity.getClass().getName();
//    log.info("bizKey:{}", bizKey);
//    MetaObject metaObject = SystemMetaObject.forObject(entity);
//    String name = (String) metaObject.getValue("name");
    final long id = al.getAndAdd(1);
    log.info("为{}生成自增主键值->:{}", bizKey, id);
    return id;
  }

  @Override
  public String nextUUID(Object entity) {
    //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
//    String bizKey = entity.getClass().getName();
//    log.info("bizKey:{}", bizKey);
//    MetaObject metaObject = SystemMetaObject.forObject(entity);
//    String name = (String) metaObject.getValue("name");

//    final long id = al.getAndAdd(1);
    final String id = sequenceGenerator.genBizCode();
    return id;
  }

}