package org.example.event;

import org.example.model.User;

public class CreateAccountEvent{


    private final User user;

    public CreateAccountEvent(User user){

        this.user = user;

    }

    public User getUser() {
        return user;
    }
}
