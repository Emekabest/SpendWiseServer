package org.example.event;

import org.example.model.User;

public class UserLoginEvent {

    private User user;

    public UserLoginEvent(User user){

        this.user = user;

    }


    public User getUser() {
        return user;
    }
}
