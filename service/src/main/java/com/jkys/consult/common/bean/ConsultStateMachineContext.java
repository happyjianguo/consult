package com.jkys.consult.common.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jkys.consult.common.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@TableName(value = "consult_state_machine_context")
public class ConsultStateMachineContext extends BaseEntity<ConsultStateMachineContext> {

  private String bizCode;
  private String currentStatus;
//  private String contextObj;
  // 序列化后context
  private String contextStr;

  public ConsultStateMachineContext(String contextObj, String currentStatus, String serialize) {
    this.bizCode = contextObj;
    this.currentStatus = currentStatus;
    this.contextStr = serialize;
  }

}
