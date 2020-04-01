package com.jkys.consult.infrastructure.db.mybatisplus.component;

import com.jkys.common.db.SequenceGenerator;
import com.jkys.consult.utils.DateUtil;
import java.util.Date;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomSequenceGenerator {

  @Autowired
  private SequenceGenerator sequenceGenerator;

  private final static Object lock = "lock";

  public String genBizCode() {
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
  public String genBizCode(String bizKey) {
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