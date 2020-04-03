package com.jkys.consult.shine.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jkys.consult.shine.utils.DateDeserializer;
import com.jkys.consult.shine.utils.GsonTypeAdapter;
import com.jkys.consult.shine.utils.JsonUtil;
import com.jkys.usercenter.client.domain.Result;
import com.jkys.usercenter.client.domain.user.BaseUserInfo;
import com.jkys.usercenter.client.domain.user.UserExtraInfo;
import com.jkys.usercenter.client.service.BaseUserInfoReadService;
import com.jkys.usercenter.client.service.UserExtraReadService;
import java.text.DateFormat;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xuyandong on 2017/1/22. UserCenterUserService
 */
@Service
public class UserCenterUserService {
    private static final Logger log = LoggerFactory.getLogger(UserCenterUserService.class);
    private static Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map<String, Object>>() {}.getType
            (), new GsonTypeAdapter()).registerTypeAdapter(java.util.Date.class, new DateDeserializer())
            .setDateFormat(DateFormat.LONG).create();
    @Autowired
    private BaseUserInfoReadService baseUserInfoReadService;
    @Autowired
    private UserExtraReadService userExtraReadService;


    /**
     * 查找用户信息
     */
    public BaseUserInfo findUserById(Long id) {

        String resultJson = baseUserInfoReadService.getBaseUserById(id);
        log.info("获取用户信息结果：{}",resultJson);
        Result<BaseUserInfo> result = gson.fromJson(resultJson, new TypeToken<Result<BaseUserInfo>>() {}.getType());

        if (result.isSuccess() && result.getResult() != null) {
            return result.getResult();
        }
        return null;
    }

    public boolean isMember(Long userId) {
        Map<String, Object> userExMap = findExtraUserById(userId);
        return userExMap != null && !userExMap.isEmpty() && userExMap.get("memberLevel") != null &&
                Integer.parseInt(userExMap.get("memberLevel").toString()) > 0;
    }

    public Map<String, Object> findExtraUserById(Long id) {
        if (id == null) {
            return null;
        }
        String resultJson = userExtraReadService.findUserExtraByUserId(id, null);
        log.info("获取用户信息结果：{}", resultJson);
        Result<Map<String, Object>> result = gson.fromJson(resultJson,
                new TypeToken<Result<Map<String, Object>>>() {}.getType());

        if (result.isSuccess() && result.getResult() != null) {
            return result.getResult();
        }
        return null;
    }

    /**
     * 查找用户信息list
     */
    public Map<Long, BaseUserInfo> findUserByIds(List<Long> ids) {

        String resultJson = baseUserInfoReadService.getBaseUserByIds(ids);
        Result<Map<Long, BaseUserInfo>> result = gson.fromJson(resultJson, new TypeToken<Result<Map<Long,
            BaseUserInfo>>>() {}.getType());
        if (result.isSuccess() && result.getResult() != null) {
            return result.getResult();
        }
        return null;
    }

    /**
     * 查询是否为托管医生
     */
//    public Boolean isTransfer(Long uid) {
//        String resultJson = userExtraReadService.findUserExtraByUserId(uid, new String[]{"trusteeshipStatus"});
//        Result<Map<String, Object>> result = gson.fromJson(resultJson, new TypeToken<Result<Map<String, Object>>>()
//        {}.getType());
//        return result.isSuccess() && result.getResult() != null && result.getResult().get("trusteeshipStatus") !=
//                null && StringUtils.isNotEmpty(result.getResult().get("trusteeshipStatus").toString()) && "1".equals
//                (result.getResult().get("trusteeshipStatus").toString());
//    }

    /**
     * 批量查询用户的扩展信息
     *
     * @param selectColumn 需要返回的列
     */
    public Map<Long, UserExtraInfo> findUserExtraByUserIds(List<Long> userIds, String[] selectColumn) {
        selectColumn = null; //由于已加上了根据userId的缓存，这个值可以设置为null.对性能影响不大。
        log.info("查询用户扩展信息：{}", JsonUtil.GsonString(userIds));
        String resultJson = userExtraReadService.findUserExtraByUserIds(userIds, selectColumn);
        Result<Map<Long, UserExtraInfo>> result = gson.fromJson(resultJson, new TypeToken<Result<Map<Long,
            UserExtraInfo>>>() {}.getType());
        if (result.isSuccess() && result.getResult() != null) {
            return result.getResult();
        }
        return null;
    }
}
