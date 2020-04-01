//package com.jkys.consult.rpc;
//
//import com.jkys.consult.common.bean.User;
//import java.net.URL;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
//
//public class JsonRpcClient{
//
//  static JsonRpcHttpClient client;
//
//  public JsonRpcClient() {
//
//  }
//
//  public static void main(String[] args) throws Throwable {
//
//    // 实例化请求地址，注意服务端web.xml中地址的配置
//    try {
//      client = new JsonRpcHttpClient(new URL(
//          "http://127.0.0.1:8080/StudyJsonrpc4j/rpc"));
//
//      // 请求头中添加的信息，这里可以自己定义
//      Map<String, String> headers = new HashMap<String, String>();
//      headers.put("Name", "Key");
//
//      // 添加到请求头中去
//      client.setHeaders(headers);
//
//      //客户端doSomethine方法，通过调用远程对象的dosomething
//      JsonRpcClient test = new JsonRpcClient();
//
//      //客户端getDemo，是获取远程对象的HelloWorldBean
//      User demo = test.getDemo(1, "Hello");
//      //执行远程对象的get方法
//      System.out.println("++++++ call remote javabean obj function ++++++");
////      System.out.println(demo.getCode());
////      System.out.println(demo.getMsg());
//
//
//      //执行远程对象的方法
//      int code = test.getInt(10);
//      System.out.println("++++++ call remote function Integer：first ++++++");
//      System.out.println(code);
//
//      //调用服务端doSomething 方法
//      test.doSomething();
//
//      //第二次调用远程对象的getInt方法
//      code = test.getInt(10);
//      System.out.println("++++++ call remote function Integer：second ++++++");
//      System.out.println(code);
//
//
//      String msg = test.getString("hello");
//      System.out.println("++++++ Call remote function String  ++++++");
//      System.out.println(msg);
//      System.out.println("++++++ end ++++++");
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//  /*
//   *客户端dosomething 方法，调用服务端的dosomething
//   */
//  public void doSomething() throws Throwable {
//    client.invoke("doSomething", null);
//  }
//
//  /*
//   *客户端getDemo 方法，调用服务端的getDemo
//   */
//  public User getDemo(int code, String msg) throws Throwable {
//    String[] params = new String[] { String.valueOf(code), msg };
//    User demo = null;
//    demo = client.invoke("getDemoBean", params, User.class);
//    return demo;
//  }
//
//  /*
//   * 客户端getInt 是调用服务端getInt方法
//   */
//  public int getInt(int code) throws Throwable {
//    Integer[] codes = new Integer[] { code };
//    return client.invoke("getInt", codes, Integer.class);
//  }
//
//  /*
//   * 客户端getString 是调用服务端getString方法
//   */
//  public String getString(String msg) throws Throwable {
//    String[] msgs = new String[] { msg };
//    return client.invoke("getString", msgs, String.class);
//  }
//
//}