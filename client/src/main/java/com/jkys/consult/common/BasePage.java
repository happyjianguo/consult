package com.jkys.consult.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 自定义分页
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BasePage<T> extends Page<T> {

  private static final long serialVersionUID = 7246194974980132237L;

  private Integer pageNo;
  private String pageSize;

  public BasePage(long current, long size) {
    super(current, size);
  }
}
