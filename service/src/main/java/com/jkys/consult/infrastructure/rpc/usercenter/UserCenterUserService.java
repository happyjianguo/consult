package com.jkys.consult.infrastructure.rpc.usercenter;

import static com.jkys.consult.common.component.CodeMsg.PARAMS_ERROR;
import static com.jkys.consult.common.component.CodeMsg.SERVER_ERROR;
import static com.jkys.consult.common.constants.Constants.DOCTOR_TYPE;
import static com.jkys.consult.common.constants.Constants.PATIENT_TYPE;
import static com.jkys.usercenter.client.uenum.UserTypeEnum.DOCTOR;
import static com.jkys.usercenter.client.uenum.UserTypeEnum.PATIENT;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jkys.consult.common.bean.PatientAdvisoryInfo;
import com.jkys.consult.exception.ServerException;
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
import java.util.Objects;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Created by xuyandong on 2017/1/22. UserCenterUserService
 */
@Service
public class UserCenterUserService {

  private static final Logger log = LoggerFactory.getLogger(UserCenterUserService.class);
  private static Gson gson = new GsonBuilder()
      .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
      }.getType
          (), new GsonTypeAdapter())
      .registerTypeAdapter(java.util.Date.class, new DateDeserializer())
      .setDateFormat(DateFormat.LONG).create();
  @Resource
  private BaseUserInfoReadService baseUserInfoReadService;
  @Resource
  private UserExtraReadService userExtraReadService;

  /**
   * 查找用户信息
   */
  public BaseUserInfo findUserById(Long id) {

    String resultJson = baseUserInfoReadService.getBaseUserById(id);
    log.info("获取用户信息结果：{}", resultJson);
    Result<BaseUserInfo> result = gson.fromJson(resultJson, new TypeToken<Result<BaseUserInfo>>() {
    }.getType());

    // TODO ---- 接口目前有问题，有数据返回但是状态为false ------> todoByliming
    if (/*result.isSuccess() && */result.getResult() != null) {
      return result.getResult();
    }
    return null;
  }

  public Map<String, Object> findExtraUserById(Long id) {
    if (id == null) {
      return null;
    }
    String resultJson = userExtraReadService.findUserExtraByUserId(id, null);
    log.info("获取用户信息结果：{}", resultJson);
    Result<Map<String, Object>> result = gson.fromJson(resultJson,
        new TypeToken<Result<Map<String, Object>>>() {
        }.getType());

    // TODO ---- 接口目前有问题，有数据返回但是状态为false ------> todoByliming
    if (/*result.isSuccess() && */result.getResult() != null) {
      return result.getResult();
    }
    return null;
  }

  /**
   * 查找用户信息list
   */
  public Map<Long, BaseUserInfo> findUserByIds(List<Long> ids) {

    String resultJson = baseUserInfoReadService.getBaseUserByIds(ids);
    Result<Map<Long, BaseUserInfo>> result = gson
        .fromJson(resultJson, new TypeToken<Result<Map<Long,
            BaseUserInfo>>>() {
        }.getType());

    // TODO ---- 接口目前有问题，有数据返回但是状态为false ------> todoByliming
    if (/*result.isSuccess() && */result.getResult() != null) {
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
  public Map<Long, UserExtraInfo> findUserExtraByUserIds(List<Long> userIds,
      String[] selectColumn) {
    selectColumn = null; //由于已加上了根据userId的缓存，这个值可以设置为null.对性能影响不大。
    log.info("查询用户扩展信息：{}", JsonUtil.GsonString(userIds));
    String resultJson = userExtraReadService.findUserExtraByUserIds(userIds, selectColumn);
    Result<Map<Long, UserExtraInfo>> result = gson
        .fromJson(resultJson, new TypeToken<Result<Map<Long,
            UserExtraInfo>>>() {
        }.getType());

    // TODO ---- 接口目前有问题，有数据返回但是状态为false ------> todoByliming
    if (/*result.isSuccess() && */result.getResult() != null) {
      return result.getResult();
    }
    return null;
  }

  public boolean isVipUser(Long owner) {
    Map<String, Object> user = findExtraUserById(owner);
    if (user != null) {
      Object memberLevel = user.get("memberLevel");
      // 0 代表非会员
      return Objects.nonNull(memberLevel) && !"0".equals(memberLevel);
    }
    return false;
  }


  public boolean checkUserAuthority(Long userId, String userType) {
    if (ObjectUtils.isEmpty(userId)) {
      log.warn("咨询订单状态查询：参数异常！");
      throw new ServerException(PARAMS_ERROR);
    }

    BaseUserInfo userInfo = findUserById(userId);
    if (userInfo != null
        && ((!userInfo.getUtype().equals(DOCTOR.getValue()) && userType == DOCTOR_TYPE)
        || (!userInfo.getUtype().equals(PATIENT.getValue()) && userType == PATIENT_TYPE))) {
      throw new ServerException(SERVER_ERROR, "非法用户，请检查");
    }
    return true;
  }

  /**
   * 特殊医生组控制
   */
  private PatientAdvisoryInfo special(Long doctorId, Long patientId, Long time) {
//    log.info("特殊医生组，doctorId：{}，patientId:{}", doctorId, patientId);
    PatientAdvisoryInfo info = null;
//        patientAdvisoryInfoMapper.findByDoctorsAndPatient(SpecialDoctorHandler.allSpecialDoctor(), patientId);
//    if (info == null) {//没有咨询中的，去Im看查有没医生发起的聊天
//      PatientAdvisoryInfo lastInfo =
//          patientAdvisoryInfoMapper.findLastInfoByStatus(SpecialDoctorHandler.allSpecialDoctor(), patientId
//              , AdvisoryStatus.OVER.getCode());
//      Long outHour = System.currentTimeMillis() - FREE_TIME; //倒退47小时58分钟，2分钟空余防止咨询中消息未达
//      //48小时内有医生发消息的，最后次结束聊天后医生发起了聊天或者完全无聊天的  开启免费咨询
//      if (time > outHour) {
//        if (lastInfo == null ||
//            time - DELAY > (lastInfo.getEndTime() == null ? 0L : lastInfo.getEndTime().getTime())) {
//          info = patientAdvisoryLogic.initFreeFinishInfo(doctorId, patientId, time);
//        }
//      }
//    }
    return info;
  }

}
