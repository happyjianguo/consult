package com.jkys.consult.service;

import com.jkys.phobos.annotation.Service;

/**
 * 网关用RPC
 *
 * @author huadi
 * @version 2018/12/17
 */
@Service("template.gatewayService:1.0.0")
public interface GatewayService {

  String getAppName();
}
