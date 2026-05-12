package org.example.controller;

import org.example.model.Budget;
import org.example.model.DateTime;
import org.example.model.Transaction;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
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


    @GetMapping({"/transaction-history"})
    public List<Transaction> getHistory(@RequestParam("email") String email){

        try{
            return transactionService.get(email);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
