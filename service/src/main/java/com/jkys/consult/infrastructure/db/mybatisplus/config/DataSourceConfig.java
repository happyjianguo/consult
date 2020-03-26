package com.jkys.consult.infrastructure.db.mybatisplus.config;

//@Configuration
// TODO 已经用dynamic创建线程池了
@Deprecated
public class DataSourceConfig {

//  @Bean(name = "centerDatasource")
//  @ConfigurationProperties(prefix = "business.datasource.center")
//  public DataSource centerDatasource() {
//    return DataSourceBuilder.create().build();
//  }
//
//  @Bean(name = "companyDatasource")
//  @ConfigurationProperties(prefix = "business.datasource.company")
//  public DataSource companyDatasource() {
//    return DataSourceBuilder.create().build();
//  }
//
//  @Bean(name = "otherDatasource")
//  @ConfigurationProperties(prefix = "business.datasource.other")
//  public DataSource otherDatasource() {
//    return DataSourceBuilder.create().build();
//  }
//
//  @Bean(name = "riskDatasource")
//  @ConfigurationProperties(prefix = "business.datasource.risk")
//  public DataSource riskDatasource() {
//    return DataSourceBuilder.create().build();
//  }
//
//  @Bean(name = "drugStoreDatasource")
//  @ConfigurationProperties(prefix = "business.datasource.drugstore")
//  public DataSource drugStoreDatasource() {
//    return DataSourceBuilder.create().build();
//  }
//
//  @Bean(name = "centerDatasourceTransactionManager")
//  public DataSourceTransactionManager centerDatasourceTransactionManager(){
//    return new DataSourceTransactionManager(centerDatasource());
//  }
//
//  @Bean(name = "companyDatasourceTransactionManager")
//  public DataSourceTransactionManager companyDatasourceTransactionManager(){
//    return new DataSourceTransactionManager(companyDatasource());
//  }
//
//  @Bean(name = "otherDatasourceTransactionManager")
//  public DataSourceTransactionManager otherDatasourceTransactionManager(){
//    return new DataSourceTransactionManager(otherDatasource());
//  }

//
//  @Bean(name = "riskDatasourceTransactionManager")
//  public DataSourceTransactionManager riskDatasourceTransactionManager() {
//    return new DataSourceTransactionManager(riskDatasource());
//  }
//
//  @Bean(name = "drugStoreDatasourceTransactionManager")
//  public DataSourceTransactionManager orderDetailDatasourceTransactionManager() {
//    return new DataSourceTransactionManager(drugStoreDatasource());
//  }

/*

  @Bean(name = "centerSqlSessionFactory")
  public SqlSessionFactory centerSqlSessionFactory() throws Exception {
    MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(centerDatasource());
    sqlSessionFactory.setMapperLocations(resolveMapperLocations(environment.getProperty("business.center.mapper-locations").split(",")));
    sqlSessionFactory.setTypeAliasesPackage("com.jkys.prescriptionrisk.persistence.model");
    sqlSessionFactory.setTypeEnumsPackage("com.jkys.prescriptionrisk.persistence.enums");

    MybatisConfiguration configuration = new MybatisConfiguration();
    configuration.setJdbcTypeForNull(JdbcType.NULL);
    sqlSessionFactory.setConfiguration(configuration);

    GlobalConfig globalConfig = new GlobalConfig();
    globalConfig.setBanner(false);
    globalConfig.setDbConfig(new GlobalConfig.DbConfig().setSelectStrategy(FieldStrategy.NOT_EMPTY));
    sqlSessionFactory.setGlobalConfig(globalConfig);
    return sqlSessionFactory.getObject();
  }

  @Bean(name = "companySqlSessionFactory")
  public SqlSessionFactory companySqlSessionFactory() throws Exception {
    MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(companyDatasource());
    sqlSessionFactory.setMapperLocations(resolveMapperLocations(environment.getProperty("business.company.mapper-locations").split(",")));
    sqlSessionFactory.setTypeAliasesPackage("com.jkys.prescriptionrisk.persistence.model");
    sqlSessionFactory.setTypeEnumsPackage("com.jkys.prescriptionrisk.persistence.enums");

    MybatisConfiguration configuration = new MybatisConfiguration();
    configuration.setJdbcTypeForNull(JdbcType.NULL);
    sqlSessionFactory.setConfiguration(configuration);

    GlobalConfig globalConfig = new GlobalConfig();
    globalConfig.setBanner(false);
    globalConfig.setDbConfig(new GlobalConfig.DbConfig().setSelectStrategy(FieldStrategy.NOT_EMPTY));
    sqlSessionFactory.setGlobalConfig(globalConfig);
    return sqlSessionFactory.getObject();
  }

  @Bean(name = "otherSqlSessionFactory")
  public SqlSessionFactory otherSqlSessionFactory() throws Exception {
    MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(otherDatasource());
    sqlSessionFactory.setMapperLocations(resolveMapperLocations(environment.getProperty("business.other.mapper-locations").split(",")));
    sqlSessionFactory.setTypeAliasesPackage("com.jkys.prescriptionrisk.persistence.model");
    sqlSessionFactory.setTypeEnumsPackage("com.jkys.prescriptionrisk.persistence.enums");

    MybatisConfiguration configuration = new MybatisConfiguration();
    configuration.setJdbcTypeForNull(JdbcType.NULL);
    sqlSessionFactory.setConfiguration(configuration);

    GlobalConfig globalConfig = new GlobalConfig();
    globalConfig.setBanner(false);
    globalConfig.setDbConfig(new GlobalConfig.DbConfig().setSelectStrategy(FieldStrategy.NOT_EMPTY));
    sqlSessionFactory.setGlobalConfig(globalConfig);
    return sqlSessionFactory.getObject();
  }

 */
}