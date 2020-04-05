package com.jkys.consult.infrastructure.db.mybatisplus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.jkys.common.db.SequenceGenerator;
import com.jkys.consult.infrastructure.db.mybatisplus.component.CustomIdGenerator;
import com.jkys.consult.infrastructure.db.mybatisplus.component.CustomMetaObjectHandler;
import com.jkys.consult.infrastructure.db.mybatisplus.component.CustomSqlInjector;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
// 表示通过aop框架暴露该代理对象,AopContext能够访问
@EnableAspectJAutoProxy(exposeProxy = true)
@Slf4j
public class MybatisPlusConfig {

//  @Autowired
//  private PathMatchingResourcePatternResolver resolver;

//  @Value("${mybatis-plus.typeAliasesPackage}")
//  private String typeAliasesPackage;
//
//  @Value("${mybatis-plus.mapper-locations}")
//  private String mapperLocation;
//
//  @Value("${mybatis-plus.type-enums-package}")
//  private String typeEnumsPackage;

  /**
   * 注入主键生成器 TODO ---- 设置自增主键时，SuperEntity的id需设置IdType.AUTO，而且不能忘了设置表主键自增 ------> todoByliming
   */
  @Bean
  public IdentifierGenerator idGenerator() {
    return new CustomIdGenerator();
  }

  /**
   * 注入sql注入器
   */
  @Bean
  public ISqlInjector sqlInjector() {
    return new CustomSqlInjector();
  }

  /**
   * 字段填充
   */
  @Bean
  public MetaObjectHandler metaObjectHandler() {
    return new CustomMetaObjectHandler();
  }

  /**
   * 分页插件，自动识别数据库类型 多租户，请参考官网【插件扩展】
   */
  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    List<ISqlParser> sqlParserList = new ArrayList<>();
    // 攻击 SQL 阻断解析器、加入解析链
//    sqlParserList.add(new BlockAttackSqlParser() {
//      @Override
//      public void processDelete(Delete delete) {
//        // 如果你想自定义做点什么，可以重写父类方法像这样子
//        if ("user".equals(delete.getTable().getName())) {
//          // 自定义跳过某个表，其他关联表可以调用 delete.getTables() 判断
//          return ;
//        }
//        super.processDelete(delete);
//      }
//    });

    // 添加动态表名解析器
/*    DynamicTableNameParser dynamicTableNameParser = new DynamicTableNameParser();
    dynamicTableNameParser.setTableNameHandlerMap(new HashMap<String, ITableNameHandler>(2) {{
      put("drug_spu", (metaObject, sql, tableName) -> {
//         metaObject 可以获取传入参数，这里实现你自己的动态规则
        String datasource = DynamicDataSourceContextHolder.peek();
        if (COMPANY.equals(datasource)) {
          return "drug_company_spu";
        } else if (CENTER.equals(datasource)) {
          return "drug_spu";
        }
        return "drug_spu";
      });
    }});
    sqlParserList.add(dynamicTableNameParser);*/
    paginationInterceptor.setSqlParserList(sqlParserList);
    // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
    // paginationInterceptor.setOverflow(false);
    // 设置最大单页限制数量，默认 500 条，-1 不受限制
    // paginationInterceptor.setLimit(500);
    // 开启 count 的 join 优化,只针对部分 left join
    paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
    return paginationInterceptor;
  }
/*

  @Bean(name = "sqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(dataSource);
    sqlSessionFactory.setMapperLocations(resolver.getResources(mapperLocation));
    sqlSessionFactory.setTypeAliasesPackage(typeAliasesPackage);
    sqlSessionFactory.setTypeEnumsPackage(typeEnumsPackage);

    MybatisConfiguration configuration = new MybatisConfiguration();
    configuration.setJdbcTypeForNull(JdbcType.NULL);
    configuration.addInterceptor(paginationInterceptor());
    configuration.setDefaultEnumTypeHandler(
        MybatisEnumTypeHandler.class);
//    configuration.setMapUnderscoreToCamelCase(true);
//    configuration.setUseGeneratedKeys(false);
//    configuration.setCacheEnabled(true);
//    configuration.setLogImpl(Slf4jImpl.class);
//    configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
//    configuration.setCallSettersOnNulls(true);

    sqlSessionFactory.setConfiguration(configuration);

    GlobalConfig globalConfig = new GlobalConfig();
    globalConfig.setBanner(false);
    globalConfig.setMetaObjectHandler(metaObjectHandler());
    globalConfig.setSqlInjector(sqlInjector());
//    globalConfig.setIdentifierGenerator(idGenerator());
    globalConfig
        .setDbConfig(new DbConfig().setSelectStrategy(FieldStrategy.NOT_EMPTY)
//            .setIdType(IdType.AUTO)
//            .setTableUnderline(true)
//            .setCapitalMode(false)
//            .setLogicDeleteValue("1")
//            .setLogicNotDeleteValue("0")
        );
    sqlSessionFactory.setGlobalConfig(globalConfig);
    return sqlSessionFactory.getObject();
  }
*/

  @Bean(name = "sequenceGenerator")
  public SequenceGenerator sequenceGenerator(
      @Value("${sequencer.driver-class-name}") String driverClass,
      @Value("${sequencer.url}") String url,
      @Value("${sequencer.username}") String username,
      @Value("${sequencer.password}") String password) {
    SequenceGenerator sequenceGenerator = null;
    try {
      sequenceGenerator = new SequenceGenerator();
      Properties config = new Properties();
      config.setProperty("driverName", driverClass);
      config.setProperty("url", url);
      config.setProperty("username", username);
      config.setProperty("password", password);
      sequenceGenerator.setDatabaseConfig(config);
      sequenceGenerator.setName("consult_id");
      sequenceGenerator.init();
    } catch (Exception e) {
      log.error("getSequenceGeneratorBean error ", e);
    }
    return sequenceGenerator;
  }

  /*
   * 乐观锁
   * */
//  @Bean
//  public OptimisticLockerInterceptor optimisticLockerInterceptor(){
//    return new OptimisticLockerInterceptor();
//  }

//  @Bean
//  public MybatisInterceptor mybatisInterceptor(){
//    MybatisInterceptor interceptor = new MybatisInterceptor();
//    return interceptor;
//  }

  /**
   * mybatis-plus分页插件<br>
   * 文档：http://mp.baomidou.com<br>
   */
/*  @Bean
  public PaginationInterceptor paginationInterceptor() {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    *//*
   * 【测试多租户】 SQL 解析处理拦截器<br>
   * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
   *//*
    List<ISqlParser> sqlParserList = new ArrayList<>();
    TenantSqlParser tenantSqlParser = new TenantSqlParser();
    tenantSqlParser.setTenantHandler(new TenantHandler() {
      @Override
      public Expression getTenantId() {
        return new LongValue(1L);
      }

      @Override
      public String getTenantIdColumn() {
        return "tenant_id";
      }

      @Override
      public boolean doTableFilter(String tableName) {
        // 这里可以判断是否过滤表
                *//*if ("user".equals(tableName)) {
                    return true;
                }*//*
        return false;
      }
    });

    sqlParserList.add(tenantSqlParser);
    paginationInterceptor.setSqlParserList(sqlParserList);
//        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
//            @Override
//            public boolean doFilter(MetaObject metaObject) {
//                MappedStatement ms = PluginUtils.getMappedStatement(metaObject);
//                // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
//                if ("com.lemon.mySharding.mapper.UserMapper.selectListBySQL".equals(ms.getId())) {
//                    return true;
//                }
//                return false;
//            }
//        });
    return paginationInterceptor;
  }*/

  /**
   * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定 配置文件和mybatis-boot的配置文件同步
   */
//  @Bean
/*  public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
    MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
    mybatisPlus.setDataSource(dataSource);
    mybatisPlus.setVfs(SpringBootVFS.class);
    if (StringUtils.hasText(this.properties.getConfigLocation())) {
      mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
    }
    if (!ObjectUtils.isEmpty(this.interceptors)) {
      mybatisPlus.setPlugins(this.interceptors);
    }

    // MP 全局配置，更多内容进入类看注释
    GlobalConfig globalConfig = GlobalConfigUtils.defaults();
    globalConfig.setSqlInjector(sqlInjector());
    //配置公共字段自动填写
    globalConfig.setMetaObjectHandler(metaObjectHandler());

    DbConfig dbConfig = new DbConfig();
    // ID 策略 AUTO->`0`("数据库ID自增") INPUT->`1`(用户输入ID") ID_WORKER->`2`("全局唯一ID") UUID->`3`("全局唯一ID")
    dbConfig.setIdType(IdType.ID_WORKER);
    globalConfig.setDbConfig(dbConfig);
    mybatisPlus.setGlobalConfig(globalConfig);

    MybatisConfiguration mc = new MybatisConfiguration();
    mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
    mybatisPlus.setConfiguration(mc);
    if (this.databaseIdProvider != null) {
      mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
    }
    if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
      mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
    }
    if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
      mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
    }
    if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
      mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
    }
    return mybatisPlus;
  }*/
}
