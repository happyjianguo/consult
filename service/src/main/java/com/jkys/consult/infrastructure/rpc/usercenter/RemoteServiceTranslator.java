package com.jkys.consult.infrastructure.rpc.usercenter;

import com.jkys.consult.infrastructure.rpc.usercenter.dataobject.UserDO;

//@Component
@Deprecated
public class RemoteServiceTranslator {

    public UserDO toUserDO(Object obj) {
        return new UserDO();
    }

}
