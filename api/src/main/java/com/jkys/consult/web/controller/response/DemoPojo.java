package com.jkys.consult.web.controller.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiecw
 * @date 2019/4/9
 */
@Deprecated
public class DemoPojo {

  private Integer id;
  private String name;
  private LocalDateTime createTime;
  private List<String> list;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public List<String> getList() {
    return list;
  }

  public void setList(List<String> list) {
    this.list = list;
  }
}
