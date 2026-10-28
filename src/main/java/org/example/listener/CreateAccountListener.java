package org.example.listener;

import org.example.event.CreateAccountEvent;
import org.example.model.User;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
public class CreateAccountListener{

    @EventListener
    public void createNewWallet(CreateAccountEvent createAccountEvent){

        try{

            User user = createAccountEvent.getUser();

            System.out.println("Hello "+user.getFirstname() + " Your Wallet Id is " + user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
