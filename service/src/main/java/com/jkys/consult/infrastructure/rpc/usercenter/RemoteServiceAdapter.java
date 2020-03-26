package com.jkys.consult.infrastructure.rpc.usercenter;

import com.jkys.consult.common.model.User;
import com.jkys.consult.infrastructure.rpc.usercenter.dataobject.UserDO;

//@Component
@Deprecated
public class RemoteServiceAdapter {

//    @Autowired
//    private RemoteServiceTranslator translator;

    // @Autowired
    // remoteService

    public UserDO getUser(String phone) {
        // User user = remoteService.getUser(phone);
        // return this.translator.toUserDO(user);
        return null;
    }

//    public EnterpriseSegment deriveEnterpriseSegment(Cargo cargo) {
//        // remote service
//        // translator
//        return EnterpriseSegment.FRUIT;
//    }

    public boolean mayAccept(int cargoSize, User cargo) {
        // remote service
        // translator
        return true;
    }

}
