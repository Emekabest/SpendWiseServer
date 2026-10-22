package org.example.controller;

import org.example.model.DateTime;
import org.example.model.Opay;
import org.example.model.Transaction;
import org.example.service.FlutterWaveService;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OpayController {


    @Autowired
    private TransactionService transactionService;


    @Autowired
    private FlutterWaveService flutterWaveService;


    @PostMapping({"/addfunds-opay"})
    public String Request(@RequestBody Opay opay){
        try {
            String transactionRef = UUID.randomUUID().toString();


            Transaction transaction = new Transaction();

            transaction.setTransactionRef(transactionRef);
            transaction.setUserEmail(opay.getUserEmail());
            transaction.setType("funding");
            transaction.setTitle("Funding");
            transaction.setAmount(opay.getAmount());
            transaction.setTime(DateTime.getLocalDateTime());
            transaction.setStatus("pending");

            transactionService.add(transaction);


            opay.setTransactionRef(transactionRef);
            return flutterWaveService.opayPayment(opay);
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

}
