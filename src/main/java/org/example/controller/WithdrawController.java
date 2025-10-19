package org.example.controller;

import org.example.model.Withdraw;
import org.example.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;

    @PostMapping({"/withdraw"})
    public String withdraw(@RequestBody Withdraw withdraw){

        try {

            withdrawService.add(withdraw);
            withdrawService.sendEmailRequest(withdraw);

            withdrawService.updateAllBalance(withdraw);

            return "Successful";
        } catch (Exception e) {

            throw new RuntimeException(e);

        }

    }


    @GetMapping({"/getwithdraws"})
    public List<Withdraw> getWithdraws(){

        return withdrawService.get();
    }





}
