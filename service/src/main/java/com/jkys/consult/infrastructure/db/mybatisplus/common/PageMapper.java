package com.jkys.consult.infrastructure.db.mybatisplus.common;

public interface PageMapper<T> extends SuperMapper<T> {

  /**
   * 自定义分页查询
   *
   * @param T 单独 T 模块使用的分页
   * @return 分页数据
   */
//    BasePage selectUserPage(BasePage<T> T);
}
