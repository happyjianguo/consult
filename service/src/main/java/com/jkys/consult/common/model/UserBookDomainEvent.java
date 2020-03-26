package com.jkys.consult.common.model;

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
