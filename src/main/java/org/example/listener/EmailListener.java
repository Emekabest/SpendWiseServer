package org.example.listener;


import org.example.event.WithdrawEvent;
import org.example.model.Withdraw;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmailListener {

    @EventListener
    public void sendWithdrawRequestEmail(WithdrawEvent event){

        Withdraw withdraw = event.getWithdraw();

        System.out.println(withdraw.getAmount() + " withdrawal request from " + withdraw.getEmail());

    }

}
