package com.jkys.consult.service;

import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xiecw
 * @date 2019/4/9
 */
@SpringBootTest
@RunWith(SpringRunner.class)   //使用junit4进行测试
@SpringBootConfiguration
@ImportResource({"classpath*:/rpc-client.xml"})
public class RpcBaseTest {

}
