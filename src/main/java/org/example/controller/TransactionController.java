package org.example.controller;

import org.example.model.Budget;
import org.example.model.DateTime;
import org.example.model.Transaction;
import org.example.service.TransactionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private TransactionService transactionService;


    @PostMapping({"/transactions"})
    public String transaction(@RequestBody Transaction transaction){

        try{

            transaction.setTime(DateTime.getLocalDateTime());
            transactionService.add(transaction);

            return "Successful";

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }


}
