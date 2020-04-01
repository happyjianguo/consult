package com.jkys.consult.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * mapper 父类，注意这个类不要让 mp 扫描到！！
 */
public interface SuperMapper<T> extends BaseMapper<T> {

  /**
   * 自定义通用方法
   */
  Integer deleteAll();
}
