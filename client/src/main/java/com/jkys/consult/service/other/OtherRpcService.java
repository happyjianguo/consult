package com.jkys.consult.service.other;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import org.springframework.validation.annotation.Validated;

@Validated
@JsonRpcService("/other")
public interface OtherRpcService {

//  @JsonRpcMethod("OtherRpcService.createConsult")
  Boolean terminateConsult(@JsonRpcParam("consultId") String consultId);

}
