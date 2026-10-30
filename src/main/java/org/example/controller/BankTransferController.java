package org.example.controller;

import org.example.model.PaymentMethod;
import org.example.service.FlutterWaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
public class BankTransferController {

    @Autowired
    private FlutterWaveService flutterWaveService;


    @PostMapping({"/api/v1/initiate-payment/bank-transfer"})
    public ResponseEntity<?> initiate(@RequestBody PaymentMethod paymentMethod, Authentication authentication){
        String userEmail = authentication.getName();

        String transactionRef = UUID.randomUUID().toString();

        String response = flutterWaveService.bankTransferPayment(userEmail, paymentMethod.getAmount(), transactionRef);

        System.out.println(response);

        return ResponseEntity.ok(response);
    }


}
