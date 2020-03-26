package com.jkys.consult.base;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author huadi
 * @version 2019/4/19
 */
@ComponentScan(value = {"com.jkys.consult.infrastructure.db.mapper"})
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
public class DaoApplicationTest {

}
