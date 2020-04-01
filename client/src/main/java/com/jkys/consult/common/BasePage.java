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

  private long pageNo;
  private long pageSize;

  public BasePage(long pageNo, long pageSize) {
    super(pageNo, pageSize);
    this.pageNo = pageNo;
    this.pageSize = pageSize;
  }
}
