package com.jkys.consult.common.component;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 实体父类，可以放入公共字段
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class BaseEntity<T extends Model<?>> extends Model<T> {

  /**
   * 主键ID
   */
  @TableId(value = "id", type = IdType.AUTO/* value = "uid", type = IdType.ASSIGN_ID*/)
//  @TableId(value = "id", type = IdType.ASSIGN_ID)
//  @TableId(value = "id", type = IdType.ASSIGN_UUID)
//    @MyLength(value = 1, groups = {Update.class}, payload = Severity.Error.class)
  private Long id;
//    @TableField(value = "create_user", fill = FieldFill.INSERT)
//    private String createUser;

  @TableField(value = "gmt_create", fill = FieldFill.INSERT)
  private LocalDateTime gmtCreate;

//    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
//    private String updateUser;

  @TableField(value = "gmt_modify", fill = FieldFill.INSERT_UPDATE)
  private LocalDateTime gmtModify;

//    @Version
//    @TableField(value = "version", fill = FieldFill.INSERT_UPDATE, update = "%s+1")
//    private Long version;

  //  @TableField(exist=false)
//  private Set<Role> roles = new HashSet<>();
//  @TableField(value = "create_time", fill = FieldFill.INSERT)
//  private LocalDateTime createTime;
//
//  @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
//  private LocalDateTime updateTime;
//
//  @Version
//  @TableField(value = "version", fill = FieldFill.INSERT_UPDATE, update = "%s+1")
//  private Long version;

  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}