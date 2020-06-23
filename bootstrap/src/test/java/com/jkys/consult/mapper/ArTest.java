package com.jkys.consult.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jkys.consult.base.BaseTest;
import com.jkys.consult.common.bean.User;
import com.jkys.consult.infrastructure.db.mapper.UserMapper;
import java.util.Collections;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArTest extends BaseTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  public void ARInsert() {
    User user = new User();
    user.setUsername("lemon");
    user.setPassword("123");
    //字段不为空插入
    user.insert();
    //ID为空插入，否则为更新
    user.insertOrUpdate();
  }

  @Test
  public void ARUpdate() {
    User user = new User();
    user.setId(1L);
    user.setUsername("lemon");
    user.setPassword("1234");
    //ID 修改
    user.updateById();
    //条件修改
    user.update(new UpdateWrapper<User>().lambda().eq(User::getId, "1"));
  }


/*  @Test
  public void ARSelect() {
    User user = new User();
    user.setUid(5);
    //setId
    user.selectById();
    //直接键入 Id
    user.selectById(24);
    //条件
    user.selectCount(new QueryWrapper<User>().lambda().eq(User::getId, 0));
    //查询所有
    user.selectAll();
    //查询总记录数
    user.selectList(new QueryWrapper<User>().lambda().eq(User::getName, "fulinlin"));
    //查询一个
    user.selectOne(new QueryWrapper());
    //分页
    user.selectPage(new Page<>(1, 2), new QueryWrapper<>());
  }


  @Test
  public void ARDelete() {
    //删除不存在的数据 在逻辑上也是成功的，返回结果 true
    User user = new User();
    user.setId(4L);
    user.deleteById();
    user.deleteById(31);
    //条件删除
    user.delete(new QueryWrapper<User>().lambda().eq(User::getId, 3L));
  }*/

  // 普通分页
  @Test
  public void Page() {
    Page<User> page = new Page<>(1, 5);
    IPage<User> userIPage = userMapper.selectPage(page, null);
    System.out.println(Collections.unmodifiableCollection(userIPage.getRecords()));
  }

  //自定义分页
//  @Test
//  public void MyPage() {
//    BasePage<User> myPage = new BasePage<User>(1, 5).setSelectInt(10).setSelectStr("fulin");
//    BasePage<User> userMyPage = userMapper.mySelectPage(myPage);
//    System.out.println(Collections.unmodifiableCollection(userMyPage.getRecords()));
//  }
}