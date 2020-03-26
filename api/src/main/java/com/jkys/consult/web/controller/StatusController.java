package com.jkys.consult.web.controller;

import com.jkys.consult.web.controller.common.annotation.Anonymous;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huadi
 * @version 2017/8/25
 */
//@RestController
  @Deprecated
public class StatusController {

  @RequestMapping("/ping")
  @Anonymous
  public String ping() {
    return "pong";
  }
}