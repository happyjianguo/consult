package com.jkys.consult.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class MybatisCodeGenerator {

  @Test
  public void generateCode() {
    String packageName = "com.lemon.mybatisplus.generator";
    boolean serviceNameStartWithI = true;//user -> UserService, 设置成true: user -> IUserService
    generateByTables(serviceNameStartWithI, packageName, "tb_article", "tb_article_category",
        "tb_article_tags", "tb_category", "tb_comments", "tb_links", "tb_log", "tb_login_log",
        "tb_setting", "tb_tags", "tb_user");//修改为你的表名
  }

//  public static String scanner(String tip) {
//    Scanner scanner = new Scanner(System.in);
//    StringBuilder help = new StringBuilder();
//    help.append("请输入" + tip + "：");
//    System.out.println(help.toString());
//    if (scanner.hasNext()) {
//      String ipt = scanner.next();
//      if (StringUtils.isNotEmpty(ipt)) {
//        return ipt;
//      }
//    }
//    throw new MybatisPlusException("请输入正确的" + tip + "！");
//  }

  private void generateByTables(boolean serviceNameStartWithI, String packageName,
      String... tableNames) {

    String dbUrl = "jdbc:mysql://118.25.37.36:3306/tumo?useUnicode=true&characterEncoding=utf-8&useSSL=false";

    // 数据源配置
    DataSourceConfig dataSourceConfig = new DataSourceConfig()
//        .setDbType(DbType.MYSQL)
        .setUrl(dbUrl)
        .setUsername("root")
        .setPassword("123")
        .setDriverName("com.mysql.cj.jdbc.Driver");

    // 全局配置
    String projectPath = System.getProperty("user.dir");
    GlobalConfig globalConfig = new GlobalConfig()
        .setOutputDir(projectPath + "/src/main/java")
        .setActiveRecord(false)
        .setEnableCache(true)
        .setAuthor("lemon")
        .setFileOverride(true) //文件覆盖(全新文件)
        .setIdType(IdType.AUTO)//主键策略
        .setBaseResultMap(true) //SQL 映射文件
        .setBaseColumnList(true)//SQL 片段
        .setOpen(false);

    if (!serviceNameStartWithI) {
      globalConfig.setServiceName("%sService");
    }

    // 包配置
    PackageConfig packageConfig = new PackageConfig()
        .setParent(packageName)//配置父包路径
        .setModuleName("base")//配置业务包路径
        .setMapper("mybatis")
        .setController("controller")
        .setEntity("entity");

    // 策略配置
    StrategyConfig strategy = new StrategyConfig()
//        .setTablePrefix(pc.getModuleName() + "_")
        .setCapitalMode(true)
        .setEntityLombokModel(true)//是否加入lombok
        .setNaming(NamingStrategy.underline_to_camel)
        .setColumnNaming(NamingStrategy.underline_to_camel)
//        .setSuperEntityClass("com.baomidou.ant.common.BaseEntity")
        .setRestControllerStyle(true)
        .setSuperControllerClass("com.lemon.mybatisplus.common.controller.BaseController")
//        .setInclude(scanner("表名，多个英文逗号分割").split(","))
        .setInclude(tableNames)//设置表名
        .setSuperEntityColumns("id")//设置超级超级列
        .setControllerMappingHyphenStyle(true);//设置controller映射联字符

    // 自定义配置
    InjectionConfig cfg = new InjectionConfig() {
      @Override
      public void initMap() {
        // to do nothing
      }
    };

    // 如果模板引擎是 freemarker
    String templatePath = "/templates/mapper.xml.ftl";
    // 如果模板引擎是 velocity
    // String templatePath = "/templates/mapper.xml.vm";

    // 自定义输出配置
    List<FileOutConfig> focList = new ArrayList<>();
    // 自定义配置会被优先输出
    focList.add(new FileOutConfig(templatePath) {
      @Override
      public String outputFile(TableInfo tableInfo) {
        // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
        return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
            + "/" + tableInfo.getEntityName() + "Mapper.xml";
      }
    });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
    cfg.setFileOutConfigList(focList);

    // 配置模板
    TemplateConfig templateConfig = new TemplateConfig();

    // 配置自定义输出模板
    //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
    // templateConfig.setEntity("templates/entity2.java");
    // templateConfig.setService();
    // templateConfig.setController();

    templateConfig.setXml(null);

    // 代码生成器
    new AutoGenerator().setGlobalConfig(globalConfig)
        .setDataSource(dataSourceConfig)
        .setStrategy(strategy)
        .setPackageInfo(packageConfig)
//        .setCfg(cfg)
//        .setTemplate(templateConfig)
        .execute();
  }

//    private void generateByTables(String packageName, String... tableNames) {
//        generateByTables(true, packageName, tableNames);
//    }
}
