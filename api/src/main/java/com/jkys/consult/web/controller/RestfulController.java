package com.jkys.consult.web.controller;

/**
 * RESTful风格controller
 *
 * @author huadi
 */
//@RestController
//@RequestMapping("/rest") // 所有此controller下的mapping前缀
@Deprecated
public class RestfulController {
//
//  @Autowired
//  @Qualifier("demoService") // NOTE 当spring容器配置成默认byType注入，且只有唯一实现时，这个注解可以省略。
//  private DemoService demoService;
//
//
//  /*
//  API设计原则：
//  /demo 表示对所有用户的操作。
//  GET /demo 时，表示获取所有用户；
//  POST /demo 时，表示新增用户。
//  对单个用户进行操作时，在/demo 后拼接ID。例如：/demo/1
//   */
//  @RequestMapping(value = "/demo", method = POST) // HTTP method为POST，表示新增
//  public Object addDemo(@RequestBody Demo demo) {
//    demo.setUserId(RequestContext.getUser().id); // web层负责校验参数、处理请求可靠性，关键参数不能相信客户端传来的值
//    demoService.add(demo);
//
//    //HTTP规范建议返回创建的实体URL或实体信息，这里选择不返回，也可以将全部实体返回或只返回ID。
//    return new ResponseEntity(HttpStatus.CREATED); // POST表示新建，返回201。
//  }
//
//  @RequestMapping(value = "/demo", method = PUT) // HTTP method为PUT，表示修改
//  public Object updateDemo(@RequestBody Demo demo) {
//    // NOTE 注意不要相信客户端传来的ID值，要从RequestContext中取。
//    if (!Objects.equals(demo.userId, RequestContext.getUser().id)) {
//      return new ResponseEntity(HttpStatus.FORBIDDEN);
//    }
//    // PUT表示更新，在更新完成后，返回201；如果未完成资源更新（例如异步任务），返回202.
//    demoService.updateById(demo);
//    return new ResponseEntity(HttpStatus.NO_CONTENT); // PUT表示修改，在修改成功或，返回204或200。
//  }
//
//  @RequestMapping(value = "/demo/{id}", method = {GET, DELETE})
//  public Object getOrDeleteDemo(@PathVariable("id") Long id,
//      HttpMethod method) { // NOTE 这里演示了如何使用path传递参数
//    // 这里演示了在同一方法处理不同http method的方式，一般不建议这么做。
//    switch (method) {
//      case GET:
//        return demoService.getById(id);
//      case DELETE:
//        // 有web层的应用，在web层做自己的权限校验等非业务操作。这里校验删除的demo是否属于登录用户
//        Demo demo = demoService.getById(id);
//        if (!Objects.equals(demo.userId, RequestContext.getUser().id)) {
//          return new ResponseEntity(HttpStatus.FORBIDDEN);
//        }
//        demoService.delete(id); // 这里使用了内部的demoService，web层自己作为一个内部调用方负责校验权限。
//        return new ResponseEntity(HttpStatus.NO_CONTENT); // DELETE表示删除，在删除成功后，返回204或200。
//    }
//
//    return new ResponseEntity(HttpStatus.BAD_REQUEST);
//  }
//
//  /**
//   * @param page 页码，默认为1
//   * @param pageSize 每页数量，默认为20
//   */
//  @RequestMapping(value = "/demos", method = GET)
//  @AdminPrivilege // 只有admin才能分页读，使用AdminPrivilege注解的方法，会检查admin权限，没有admin权限的返回403
//  public Object getDemos(@RequestParam(value = "pageNumber", defaultValue = "1") int page,
//      @RequestParam(value =
//          "pageSize", defaultValue = "20") int pageSize) {
//    return demoService.getByPage(page, pageSize);
//  }
//
//
//  @RequestMapping(value = "/my", method = GET)
//  public Object my() {
//    // 普通用户只能读取自己的用户信息，请使用RequestContext获得用户ID，不要使用客户端传来的ID值。
//    return RequestContext.getUser().id;
//  }
//
//
//  @RequestMapping("/item/{itemId}")
//  @Anonymous // 未登录用户，也能浏览商品信息，使用Anonymous修饰此方法。
//  public Object getItem(@PathVariable("itemId") Long itemId) {
//    if (itemId < 0) {
//      // BizException会返回400，message需要时用户友好的提示。
//      throw new DemoException("您输入的信息有误。（商品ID不能为负数。）");
//    }
//
//    if (itemId == 0) {
//      return new ResponseEntity(HttpStatus.NOT_FOUND); // 需要自定义返回状态码时，直接返回ResponseEntity。
//    }
//
//    // REST均返回JSON，如返回简单格式，可直接返回匿名类或Map。
//    return new Object() {
//      /*
//      实现getXX方法，JSON框架会序列化成XX属性
//      这个例子中，如果itemId=1，返回的response JSON为：{"id": 1, "name": "Item with ID 1"}
//       */
//      public Long getId() {
//        return itemId;
//      }
//
//      public String getName() {
//        return "Item with ID " + itemId;
//      }
//    };
//  }

}
