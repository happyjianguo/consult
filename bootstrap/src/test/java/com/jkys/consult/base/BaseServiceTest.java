package com.jkys.consult.base;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huadi
 * @version 2019/4/19
 */
@Ignore // NOTE 自动测试忽略此类
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplicationTest.class)
@TestPropertySource("file:/data/www/91jkys/config/template.properties")
@ContextConfiguration(locations = {"classpath:/spring/*.xml", "classpath*:/rpc-*.xml"}) //加载配置文件
public class BaseServiceTest {

  static {
    System.setProperty("app.name", "template");
  }
}
