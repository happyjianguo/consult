package com.jkys.consult.infrastructure.db.mybatisplus.component;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.jkys.common.db.SequenceGenerator;
import com.jkys.consult.utils.DateUtil;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CustomIdGenerator implements IdentifierGenerator {

  @Autowired
  private SequenceGenerator sequenceGenerator;

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
    String bizKey = "WZ";
//    final long id = al.getAndAdd(1);
    final String id = genBizCode(bizKey);
        log.info("为{}生成主键值->:{}", bizKey, id);
    return id;
  }


  /**
   * mysql 生成支付单号
   *
   * @return code
   */
  private String genBizCode(String bizKey) {
    return bizKey + DateUtil.format(new Date(), DateUtil.PATTERN_YEAR2DAY) + StringUtils
        .leftPad(sequenceGenerator.get() + "", 7, '0');
  }

  /**
   * 生成业务单号
   *
   * @return ordernum
   */
  public String genOrderNum(String bizKey) {
    synchronized (lock) {
      Random random = new Random();
      final Integer rand = 10000;
      int num = random.nextInt(rand);
      return bizKey + DateUtil.format(new Date(), DateUtil.PATTERN_YEAR2DAY) + StringUtils.leftPad(Integer.toString(num), 7, '0');
    }
  }

}