package org.example.listener;


import org.example.event.WithdrawEvent;
import org.example.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WithdrawListener {

    private final EmailService emailService;

    public WithdrawListener(EmailService emailService){
        this.emailService = emailService;

    }

    @EventListener
    public void sendEmailEvent(WithdrawEvent event){



        try {


        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }

    @EventListener
    public void transaction(WithdrawEvent event){

//        Withdraw withdraw = event.getWithdraw();

        try {


        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }

}
