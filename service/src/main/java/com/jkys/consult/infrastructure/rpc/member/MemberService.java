package com.jkys.consult.infrastructure.rpc.member;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jkys.consult.shine.utils.DateDeserializer;
import com.jkys.consult.shine.utils.GsonTypeAdapter;
import com.jkys.pt.client.service.member.MemberAdvisoryRpcService;
import java.text.DateFormat;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberService {

  private static Gson gson = new GsonBuilder()
      .registerTypeAdapter(new TypeToken<Map<String, Object>>() {
      }.getType
          (), new GsonTypeAdapter())
      .registerTypeAdapter(java.util.Date.class, new DateDeserializer())
      .setDateFormat(DateFormat.LONG).create();

  @Resource
  private MemberAdvisoryRpcService memberAdvisoryRpcService;

  /**
   * 用户剩余的免费次数
   */
//  public Integer getMemberFreeTimes(Long patientId, Long doctorId) {
//    Integer memberFree = 0;
//    if (isMember(patientId)) {
//      memberFree = memberAdvisoryRpcService
//          .getFreeAdvisoryByDocId(patientId, doctorId);
//    }
//    return memberFree;
//  }

//  public boolean isMember(Long userId) {
//    Map<String, Object> userExMap = findExtraUserById(userId);
//    return userExMap != null && !userExMap.isEmpty() && userExMap.get("memberLevel") != null &&
//        Integer.parseInt(userExMap.get("memberLevel").toString()) > 0;
//  }


  /**
   * 扣除用户免费次数
   */
  public Boolean deductMemberFreeTimes(String orderId, Long patientId, Long doctorId, String client, Boolean mock) {
    // TODO ---- 测试用 ------> todoByliming
    if (mock) {
      return true;
    }
    return false;
//    PayOrderResponse response = PayOrderResponse.builder()
//        .consultId(order.getConsultId())
//        .orderId(order.getOrderId())
//        .price(order.getPrice())
//        .build();
//    if (request.getMock()) {
//      response.setCoinPay(COIN_SUCCESS);
//    } else {
//      response.setCoinPay(COIN_FAIL);
//    }
//    return response;
//    Integer memberFreeTimes = getMemberFreeTimes(patientId, doctorId);
//    if (!ObjectUtils.isEmpty(memberFreeTimes) && memberFreeTimes > 0) {
//      // TODO ---- addFreeAdvisory 是否调整第一个参数为String orderId ------> todoByliming
//      boolean member = memberAdvisoryRpcService.addFreeAdvisory(id, patientId,
//          doctorId, client);
//      if (!member) {
//        throw new ServerException(SERVER_ERROR, "请检查您的会员咨询权限或退出重试!");
//      }
//    }
  }

}
