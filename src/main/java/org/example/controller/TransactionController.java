package org.example.controller;

import org.example.model.Transaction;
import org.example.service.TransactionService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;


    @GetMapping({"/api/v1/transaction/history"})
    public Page<Transaction> transaction(Pageable pageable, Authentication authentication){

        try{
            String userEmail = authentication.getName();
            int userId = userService.getUser(userEmail).getId();

            return transactionService.getTransactions(userId, pageable);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }




}
