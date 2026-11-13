package org.example.controller;

import org.example.dto.BankAccountDetails;
import org.example.model.Withdraw;
import org.example.service.FlutterWaveService;
import org.example.service.UserService;
import org.example.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController

public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;


    @Autowired
    private FlutterWaveService flutterWaveService;

    @Autowired
    private UserService userService;

    @PostMapping({"/api/v1/withdraw"})
    public String withdraw(@RequestBody Withdraw withdraw){

        try {
            String transactionRef = UUID.randomUUID().toString();


            flutterWaveService.transfer(
                    withdraw.getAccountNumber(),
                    withdraw.getBank(),
                    withdraw.getAmount(),
                    transactionRef
            );

            return "Successful";
        } catch (Exception e) {

            System.out.println("This error occurred::"+e.getMessage());
            throw new RuntimeException(e);
        }

    }



    @GetMapping("/api/v1/get-banks")
    public String getBanks() {

        try {
            return flutterWaveService.getBanks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/api/v1/confirm-bank")
    public  String confirmBank(@RequestBody BankAccountDetails bankAccountDetails, Authentication authentication){

        try {
            String userEmail = authentication.getName();

            return flutterWaveService.confirmBankAccount(
                    bankAccountDetails.accountNumber(),
                    bankAccountDetails.accountBank()
            );
        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }


}
