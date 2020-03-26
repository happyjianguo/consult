package com.jkys.consult.service;

import com.jkys.phobos.annotation.Service;

/*
 * NOTE 开放给其他系统使用的service，在client中放置interface。注意这里演示的是给内部应用调用的service，所以没有权限校验。
 */
@Service("template.demoRpcService:1.0.0")
public interface DemoRpcService {
//    Demo getById(long id);
//    Demo getByCondition(Demo demo);
}
