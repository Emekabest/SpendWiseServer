package org.example.controller;

import org.example.model.PaymentMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BankTransferController {


    @PostMapping({"/api/v1/initiate-payment/bank-transfer"})
    public ResponseEntity<?> initiate(@RequestBody PaymentMethod paymentMethod){

        System.out.println("This is payment method::"+paymentMethod.getAmount() + " " + paymentMethod.getUserEmail());

        return ResponseEntity.ok().build();
    }


}
