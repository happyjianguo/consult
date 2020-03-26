package com.jkys.consult.service;

/**
 * 内部使用的DemoService。如果确定只有内部使用，可使用class直接实现，不需要定义interface。
 *
 * @author huadi
 * @version 2018/11/23
 */
//@Service
  @Deprecated
public class DemoService {
//    @Autowired
//    private DemoMapper demoMapper;
//
//    @Autowired
//    private MultiNameSequenceGenerator sequenceGenerator;
//
//    private static final String DEMO_ID_PK_NAME = "demo"; // NOTE 在db.sql中，已经增加了一个demo的sequence数据
//
//    public Demo getById(long id) {
//        return demoMapper.selectByPrimaryKey(id);
//    }
//
//    public Collection<Demo> getByPage(int pageNumber, int pageSize) {
//        return Collections.emptyList();
//    }
//
//    public void add(Demo demo) {
//        demo.id = sequenceGenerator.get(DEMO_ID_PK_NAME);
//        //insert分insert完整字段插入和insertSelective不为null字段插入
//        demoMapper.insert(demo);
//    }
//
//    public void delete(long id) {
//        //逻辑删除可以考虑开一个自定义通用接口
//        demoMapper.deleteByPrimaryKey(id);
//    }
//
//    public void updateById(Demo demo) {
//        //update分updateByPrimaryKey完整字段更新和updateByPrimaryKeySelective不为null字段更新
//        demoMapper.updateByPrimaryKey(demo);
//    }
}
