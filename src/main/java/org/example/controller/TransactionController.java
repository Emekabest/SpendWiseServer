package org.example.controller;

import org.example.model.DateTime;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.service.TransactionService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;


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
            User user = userService.getUser(email);

            return transactionService.getAll(user.getId());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
