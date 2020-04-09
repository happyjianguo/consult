package com.jkys.consult.request;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkys.consult.common.component.BasePage;
import com.jkys.phobos.exception.ServiceError;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

/**
 * 自定义分页
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
public class PageRequest<T> extends Page<T> {

  private static final long serialVersionUID = 7246194974980132237L;

  private Integer pageNo;
  private Integer pageSize;

  private T request;

  public PageRequest(Integer pageNo, Integer pageSize, T request) {
    super(Long.valueOf(pageNo.toString()), Long.valueOf(pageSize.toString()));
    if (ObjectUtils.isEmpty(request)) {
      throw new ServiceError("0", "request不能为空");
    }
    if (!StringUtils.isNumeric(String.valueOf(pageNo))) {
      throw new ServiceError("0", "pageNo必须为数值");
    }
    if (pageNo <= 0) {
      throw new ServiceError("0", "pageNo必须大于0");
    }
    if (!StringUtils.isNumeric(String.valueOf(pageSize))) {
      throw new ServiceError("0", "pageSize必须为数值");
    }
    if (pageSize <= 0) {
      throw new ServiceError("0", "pageSize必须大于0");
    }
    this.request = request;
    this.pageNo = pageNo;
    this.pageSize = pageSize;
  }

  public BasePage getPage() {
    BasePage basePage = BasePage.builder()
        .pageNo(Long.valueOf(this.pageNo.toString()))
        .pageSize(Long.valueOf(this.pageSize.toString()))
        .build();
    return basePage;
  }

//  public T getRequest(Class<T> clazz) {
//    return clazz.cast(this.request);
//  }
}
