package com.jkys.consult.base;

import com.jkys.consult.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: daobinwang@91jkys.com
 * @create: 2019/11/30 22:09
 **/
@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {SqlApplication.class, DataSourceAutoConfiguration.class})
@SpringBootTest(classes = {Application.class})
//@WebAppConfiguration
public abstract class BaseTest {

}
