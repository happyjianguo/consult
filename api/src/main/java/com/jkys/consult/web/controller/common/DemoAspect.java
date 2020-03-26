package com.jkys.consult.web.controller.common;

/**
 * @author huadi
 * @version 2019/4/16
 */
//@Component
  @Deprecated
public class DemoAspect extends ControllerAspect {

  @Override
  protected boolean isLegalToken(String token) {
    return true;
  }

  @Override
  protected boolean isAdmin(String token) {
    return true;
  }
}
