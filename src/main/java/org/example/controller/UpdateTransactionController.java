package org.example.controller;

import org.example.model.Transaction;
import org.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateTransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping({"/update-transaction"})
    public void update(@RequestBody Transaction transaction){

        try{
            System.out.println("ID::"+transaction.getId());
            System.out.println("Status::"+transaction.getStatus());

            transactionService.update(transaction);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
