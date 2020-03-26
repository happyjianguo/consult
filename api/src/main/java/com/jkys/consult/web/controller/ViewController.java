package com.jkys.consult.web.controller;

import com.jkys.consult.web.controller.common.annotation.Anonymous;
import com.jkys.consult.web.controller.response.DemoPojo;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * springmvc请求返回pojo、freemarker例子
 *
 * @author huadi
 * @version 2017/8/24
 */
//@RestController
//@RequestMapping("/view")
  @Deprecated
public class ViewController {

  @RequestMapping("/init")
  @Anonymous
  public String init() {
    return "welcome";
  }

  @RequestMapping("/pojo")
  @Anonymous
  public DemoPojo pojo() {
    DemoPojo demo = new DemoPojo();
    demo.setName("jkys");
    demo.setCreateTime(LocalDateTime.now());
    return demo;
  }

  @RequestMapping("/freemarker")
  @Anonymous
  public ModelAndView freemarker(DemoPojo demo) {
    ModelAndView modelAndView = new ModelAndView();

    demo.setId(demo.getId() == null ? 990 : demo.getId());
    demo.setName(demo.getName() == null ? "中文" : demo.getName());
    demo.setList(demo.getList() == null ? Arrays.asList("aa", "bb") : demo.getList());
    modelAndView.addObject("jsonBean", demo);
    modelAndView.setViewName("demo");

    return modelAndView;
  }
}
