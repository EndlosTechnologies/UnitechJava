package com.unitechApi.EventListener;

import com.unitechApi.user.model.User;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class OnRegisterEvent extends ApplicationEvent {
    private User user;
    private String name;

    public OnRegisterEvent(User  user) {
        super(user);
        this.user=user;
    }

    public OnRegisterEvent(Object source, User user, String name) {
        super(source);
        this.user = user;
        this.name = name;
    }

    public OnRegisterEvent(Object source, Clock clock, User user, String name) {
        super(source, clock);
        this.user = user;
        this.name = name;
    }

    public User getUser()
    {
        return user;
    }
    public OnRegisterEvent setUser(User  user) {

        this.user=user;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
