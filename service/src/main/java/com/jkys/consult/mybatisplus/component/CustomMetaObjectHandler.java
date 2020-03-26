package com.jkys.consult.mybatisplus.component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
 * mybatisplus自定义填充公共字段 ,即没有传的字段自动填充
 */
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {

  //新增填充
  @Override
  public void insertFill(MetaObject metaObject) {
    log.info("新增的时候干点不可描述的事情");
//		Object operator = metaObject.getValue("createUser");
    //获取当前登录用户
    //SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
//		if (null == operator) {
//    this.setInsertFieldValByName("createUser", "createUser", metaObject);
    this.setInsertFieldValByName("createTime", LocalDateTime.now(ZoneId.of("Asia/Shanghai")),
        metaObject);

    log.info("LocalDateTime: {}", metaObject.getValue("createTime"));

//    this.setInsertFieldValByName("updateUser", "updateUser", metaObject);
    this.setInsertFieldValByName("modifyTime", LocalDateTime.now(ZoneId.of("Asia/Shanghai")),
        metaObject);

    log.info("LocalDateTime: {}", metaObject.getValue("modifyTime"));

//    this.setUpdateFieldValByName("version", 1L, metaObject);
//		}

  }

  //更新填充
  @Override
  public void updateFill(MetaObject metaObject) {
    log.info("更新的时候干点不可描述的事情");
//    this.setUpdateFieldValByName("updateUser", "updateUser", metaObject);
    this.setUpdateFieldValByName("modifyTime", LocalDateTime.now(ZoneId.of("Asia/Shanghai")),
        metaObject);
  }
}
