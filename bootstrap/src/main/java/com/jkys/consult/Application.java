package com.jkys.consult;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author huadi
 * @version 2020/3/26
 */
@ImportResource({"classpath*:/rpc-*.xml"})
//@PropertySource({"file:/data/www/91jkys/config/consult.properties","file:/data/www/91jkys/config/mysql.properties"})
@PropertySource(value = {
    "classpath:config.properties",
    "file:${data.config.dir.path}/consult.properties",
    "file:${data.config.dir.path}/mysql.properties",
//    "file:${data.config.dir.path}/redis.properties",
    "file:${data.config.dir.path}/kafka.properties",
    "file:${data.config.dir.path}/mq.properties"
    /*"classpath:env.properties"*/})
@SpringBootApplication(exclude = {
    HibernateJpaAutoConfiguration.class})
//@SpringBootApplication(exclude = {io.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration.class/*, DataSourceAutoConfiguration.class*/})
@EnableScheduling
@EnableAsync
// TODO ---- 临时加上，之后整合 ------> todoByliming
@MapperScan(basePackages = {"com.jkys.consult.infrastructure.db.mapper,com.jkys.consult.shine.mapper"})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
