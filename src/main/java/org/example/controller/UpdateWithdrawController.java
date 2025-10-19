package org.example.controller;

import org.example.model.Withdraw;
import org.example.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateWithdrawController {

    @Autowired
    private WithdrawService withdrawService;


    @PostMapping({"/updatewithdraw"})
    public String updateWithdraw(@RequestBody Withdraw withdraw){
        try {

            withdrawService.update(withdraw);

            return "Successful";
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }
}
