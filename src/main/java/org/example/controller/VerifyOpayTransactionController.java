package org.example.controller;


import org.example.enums.TransactionStatus;
import org.example.model.PaymentMethod;
import org.example.model.Transaction;
import org.example.service.FlutterWaveService;
import org.example.service.TransactionService;
import org.example.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class VerifyOpayTransactionController {


    @Autowired
    private FlutterWaveService flutterWaveService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletService walletService;


    @PostMapping({"/verify-payment/opay"})
    public ResponseEntity<?> request(@RequestBody PaymentMethod paymentMethod){

        Map<String, Object> response =  new HashMap<>();

        try{
            Transaction transaction = transactionService.get(paymentMethod.getTransactionRef());

            TransactionStatus paymentStatus = transaction.getStatus();

            if (TransactionStatus.failed.equals(paymentStatus)){

                response.put("payment_status", TransactionStatus.failed);
                return ResponseEntity.ok(response);
            }

            if (TransactionStatus.successful.equals(paymentStatus)){

                response.put("payment_status", TransactionStatus.successful);

                return ResponseEntity.ok(response);
            }


            response.put("payment_status", TransactionStatus.pending);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
