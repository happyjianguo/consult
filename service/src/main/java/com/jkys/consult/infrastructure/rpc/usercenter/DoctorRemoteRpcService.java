package com.jkys.consult.infrastructure.rpc.usercenter;

/**
 * 调用个人中心医生信息服务
 */
public interface DoctorRemoteRpcService {

  Integer getDoctorPrice(Long doctorId);

}
