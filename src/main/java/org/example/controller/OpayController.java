package org.example.controller;

import org.example.model.DateTime;
import org.example.model.Opay;
import org.example.model.Transaction;
import org.example.service.FlutterWaveService;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController

public class OpayController {


    @Autowired
    private TransactionService transactionService;


    @Autowired
    private FlutterWaveService flutterWaveService;


    @PostMapping({"/initiate-payment/opay"})
    public ResponseEntity<?> initiate(@RequestBody Opay opay){
        try {
            String transactionRef = UUID.randomUUID().toString();


            Transaction transaction = new Transaction(transactionRef, opay.getUserEmail(), "addfunds", opay.getAmount(), DateTime.getLocalDateTime(), "pending");

            opay.setTransactionRef(transactionRef);
            String responseData = flutterWaveService.opayPayment(opay);

            transactionService.add(transaction);

            return ResponseEntity.ok(
                    Map.of(
                            "responseData", responseData,
                            "transactionRef", transactionRef
                    )
            );
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }


    @GetMapping({"/opaypaymentcallback"})
    public ResponseEntity<String> paymentCallback(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String tx_ref,
            @RequestParam(required = false) String transaction_id
    ){

        System.out.println("Callback status: " + status);
        System.out.println("Transaction ref: " + tx_ref);
        System.out.println("Transaction ID: " + transaction_id);

        return ResponseEntity.ok("Callback received");
    }

}
