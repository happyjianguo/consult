package com.jkys.consult.infrastructure.mapstrut;

import com.jkys.consult.statemachine.enums.ConsultStatus;
import org.springframework.stereotype.Component;

@Component
public class consultStateFormat {
  public Integer getStatus(ConsultStatus consultStatus) {
    return consultStatus.getType().getCode();
  }
}
