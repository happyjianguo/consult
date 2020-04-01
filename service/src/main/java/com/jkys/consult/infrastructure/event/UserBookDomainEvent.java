package com.jkys.consult.infrastructure.event;

import com.jkys.consult.common.bean.User;

@Deprecated
public class UserBookDomainEvent {

    private User user;

    public UserBookDomainEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
