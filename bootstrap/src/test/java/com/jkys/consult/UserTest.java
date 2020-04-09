package com.jkys.consult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Stopwatch;
import com.jkys.consult.base.BaseTest;
import com.jkys.consult.infrastructure.rpc.usercenter.UserCenterUserService;
import com.jkys.usercenter.client.domain.Result;
import com.jkys.usercenter.client.domain.user.BaseUserInfo;
import com.jkys.usercenter.client.domain.user.UserExtraInfo;
import com.jkys.usercenter.client.service.BaseUserInfoReadService;
import com.jkys.usercenter.client.service.UserExtraReadService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class UserTest extends BaseTest {

  @Autowired
  private UserCenterUserService userCenterUserService;

  @Autowired
  private BaseUserInfoReadService baseUserInfoReadService;

  @Autowired
  private UserExtraReadService userExtraReadService;
//  @Autowired(required = false)
//  BaseUserProxy baseUserProxy;
//
//  @Autowired(required = false)
//  UserExtraProxy userExtraProxy;

  private static Stopwatch stopwatch;

  @BeforeEach
  void setUp() {
    stopwatch = Stopwatch.createStarted();
  }

  @AfterAll
  static void afterAll() {
    log.error(stopwatch.toString());
  }

  @Test
  public void findUserExtraByUserIds() {
    List<Long> userIds = new ArrayList<>();
    userIds.add(patientId);
    userIds.add(doctorId);
//    getBaseUserById(patientId);
//    Result<BaseUserInfo> patientInfo = baseUserProxy.getBaseUserById(patientId);
//    Result<Map<String, Object>> patientInfoExtra = userExtraProxy.findUserExtraByUserId(patientId, null);
//    Result<Map<Long, BaseUserInfo>> patientInfoMap = baseUserProxy.getBaseUserByIds(userIds);
    BaseUserInfo patientInfo = userCenterUserService.findUserById(119222904L);
    Map<String, Object> patientInfoExtra = userCenterUserService.findExtraUserById(patientId);
    Map<Long, BaseUserInfo> patientInfoMap = userCenterUserService.findUserByIds(userIds);
//    Boolean result3 = userCenterUserService.isTransfer(44612298L);
    Map<Long, UserExtraInfo> patientInfoExtraMap = userCenterUserService.findUserExtraByUserIds(userIds, null);

    System.out.println("ddddddd");
  }

  /**
   * 通过用户id，查询用户基础信息
   * @param id
   * @return
   */
  public Result<BaseUserInfo> getBaseUserById(Long id){

    String resultJson = baseUserInfoReadService.getBaseUserById(id);
    Result<BaseUserInfo> result = JSON
        .parseObject(resultJson, new TypeReference<Result<BaseUserInfo>>(){});
    return  result;
  }

  /**
   * 列表的形式，批量查询
   * @param ids
   * @return
   */
  public Result<Map<Long, BaseUserInfo>> getBaseUserByIds(List<Long> ids){

    String resultJson = baseUserInfoReadService.getBaseUserByIds(ids);
    Result<Map<Long, BaseUserInfo>> result = JSON.parseObject(resultJson, new TypeReference<Result<Map<Long, BaseUserInfo>>>(){});
    return result;
  }

  /**
   * 查询用户的扩展信息
   * @param userId		用户id
   * @param selectColumn 需要返回的列
   * @return
   */
  public Result<Map<String, Object>> findUserExtraByUserId(Long userId, String[] selectColumn){
    selectColumn = null; //由于已加上了根据userId的缓存，这个值可以设置为null.对性能影响不大。

    String resultJson = userExtraReadService.findUserExtraByUserId(userId, selectColumn);
    Result<Map<String, Object>> result = JSON.parseObject(resultJson, new TypeReference<Result<Map<String, Object>>>(){});

    return result;
  }

  /**
   * 批量查询用户的扩展信息
   * @param userIds
   * @param selectColumn 	需要返回的列
   * @return
   */
  public Result<Map<Long,UserExtraInfo>> findUserExtraByUserIds(List<Long> userIds, String[] selectColumn) {
    selectColumn = null; //由于已加上了根据userId的缓存，这个值可以设置为null.对性能影响不大。

    String resultJson = userExtraReadService.findUserExtraByUserIds(userIds, selectColumn);
    Result<Map<Long,UserExtraInfo>> result = JSON.parseObject(resultJson, new TypeReference<Result<Map<Long,UserExtraInfo>>>(){});
    return result;
  }

}