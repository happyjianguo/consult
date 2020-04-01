package com.jkys.consult.infrastructure.rpc.usercenter.fake;

import com.jkys.consult.infrastructure.rpc.usercenter.DoctorRemoteRpcService;
import org.springframework.stereotype.Service;

@Service
public class DoctorRemoteRpcServiceImpl implements DoctorRemoteRpcService {

  // TODO ---- 待定 ------> todoByliming
  @Override
  public Integer getDoctorPrice(Long doctorId) {
    return 10;
  }
}
