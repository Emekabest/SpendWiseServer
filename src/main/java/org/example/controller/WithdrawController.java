package org.example.controller;

import org.example.model.Withdraw;
import org.example.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;



    @PostMapping({"/api/v1/withdraw"})
    public String withdraw(@RequestBody Withdraw withdraw){

        try {

            System.out.println(withdraw.getAccountNumber() + "::" + withdraw.getBank() + "::" + withdraw.getAmount());

            return "Successful";
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }
}
