package com.jkys.consult.web.controller;

import com.jkys.consult.web.controller.common.annotation.Anonymous;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liming
 * @version 2020/3/26
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