package org.example.listener;

import org.example.event.CreateAccountEvent;
import org.example.model.User;
import org.example.model.Wallet;
import org.example.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class CreateAccountListener{

    private final WalletService walletService;


    @Autowired
    public CreateAccountListener(WalletService walletService){
        this.walletService = walletService;

    }

    @EventListener
    public void createNewWallet(CreateAccountEvent createAccountEvent){

        try{
            User newUser = createAccountEvent.getUser();

            Wallet wallet = new Wallet(newUser.getId(), new BigDecimal("0.00"), new BigDecimal("0.00"));

            walletService.add(wallet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
