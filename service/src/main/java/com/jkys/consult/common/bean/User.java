package com.jkys.consult.common.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jkys.consult.common.BaseEntity;
import com.jkys.consult.infrastructure.db.mybatisplus.validate.MyLength;
import com.jkys.consult.infrastructure.db.mybatisplus.validate.groups.Create;
import com.jkys.consult.infrastructure.db.mybatisplus.validate.groups.Update;
import com.jkys.consult.infrastructure.db.mybatisplus.validate.payload.Severity;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Builder
//@RequiredArgsConstructor(staticName = "of")
@TableName(value = "user")
@Deprecated
// TODO ---- 测试用 ------> todoByliming
public class User extends BaseEntity<User>/* implements Serializable */ {

//  private static final long serialVersionUID = 1L;

  // 用户名
  @NotNull
  @MyLength(value = 2, groups = {Create.class, Update.class}, payload = {Severity.Info.class})
  private String username;
  // 密码
  private String password;
  // 盐值
//  private String salt;
  // 头像
//  private String avatar;
  // 介绍
//  private String introduce;
  // 备注
//  private String remark;

//  @TableField(exist=false)
//  private Set<Role> roles = new HashSet<>();

}