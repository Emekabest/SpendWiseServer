package org.example.controller;

import org.example.model.Withdraw;
import org.example.service.FlutterWaveService;
import org.example.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping({"/api/v1/withdraw"})
    public String withdraw(@RequestBody Withdraw withdraw){//

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
}
