package com.jkys.consult.infrastructure.db.mybatisplus.component;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.jkys.consult.infrastructure.db.mybatisplus.commonMethods.DeleteAll;
import java.util.List;

/**
 * 自定义 SqlInjector
 */
public class CustomSqlInjector extends DefaultSqlInjector {

  @Override
  public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
    List<AbstractMethod> methodList = super.getMethodList(mapperClass);
    //增加自定义方法
    methodList.add(new DeleteAll());
    return methodList;
  }
}
