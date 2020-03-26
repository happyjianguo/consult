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
@RunWith(SpringRunner.class)  //使用junit4进行测试
@SpringBootTest(classes = DaoApplicationTest.class)
@ContextConfiguration(locations = {"classpath:/spring/*.xml"}) //加载配置文件
@TestPropertySource("file:/data/www/91jkys/config/template.properties")
public class BaseDaoTest {

}
