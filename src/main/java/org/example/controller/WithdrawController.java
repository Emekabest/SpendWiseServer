package org.example.controller;

import org.example.dto.BankAccountDetails;
import org.example.dto.WithdrawRequest;
import org.example.enums.TransactionStatus;
import org.example.enums.TransactionType;
import org.example.model.DateTime;
import org.example.model.Transaction;
import org.example.service.FlutterWaveService;
import org.example.service.TransactionService;
import org.example.service.UserService;
import org.example.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
public class WithdrawController {

    @Autowired
    private WithdrawService withdrawService;


    @Autowired
    private FlutterWaveService flutterWaveService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;



    @PostMapping({"/api/v1/withdraw"})
    public String withdraw(@RequestBody WithdrawRequest withdrawRequest, Authentication authentication){

        try {

            System.out.println("Reached here...0");


            String userEmail = authentication.getName();
            int userId = userService.getUser(userEmail).getId();

            String transactionRef = UUID.randomUUID().toString();

            System.out.println("Reached here...1");


            String response = flutterWaveService.transfer(
                withdrawRequest.accountNumber(),
                withdrawRequest.bank(),
                withdrawRequest.amount(),
                transactionRef
            );
            System.out.println("Reached here...2");


            Transaction transaction = new Transaction(
                    transactionRef,
                    userId,
                    TransactionType.withdrawal,
                    BigDecimal.valueOf(withdrawRequest.amount()),
                    DateTime.getLocalDateTime(),
                    TransactionStatus.pending
            );

            transactionService.add(transaction);

            System.out.println("Reached here...3");



            return response;
        } catch (RuntimeException e) {

            throw new RuntimeException(e);
        }

    }

    @GetMapping("/api/v1/withdraw/status")
    public String withdrawStatus(@RequestParam String withdrawId){

        try {

            return flutterWaveService.transferStatus(withdrawId);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }



    @GetMapping("/api/v1/get-banks")
    public String getBanks() {

        try {
            return flutterWaveService.getBanks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PostMapping("/api/v1/confirm-bank")
    public  String confirmBank(@RequestBody BankAccountDetails bankAccountDetails, Authentication authentication){

        try {
            String userEmail = authentication.getName();

            return flutterWaveService.confirmBankAccount(
                    bankAccountDetails.accountNumber(),
                    bankAccountDetails.accountBank()
            );
        }
        catch (HttpClientErrorException.BadRequest e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Account number is invalid"
            );
        }
        catch (Exception e) {

            throw new RuntimeException(e);
        }

    }





}
