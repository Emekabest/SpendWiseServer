package org.example.controller;

import org.example.enums.TransactionStatus;
import org.example.enums.TransactionType;
import org.example.model.DateTime;
import org.example.model.PaymentMethod;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.service.FlutterWaveService;
import org.example.service.TransactionService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;


@RestController
public class BankTransferController {

    @Autowired
    private FlutterWaveService flutterWaveService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;


    @PostMapping({"/api/v1/initiate-payment/bank-transfer"})
    public ResponseEntity<?> initiate(@RequestBody PaymentMethod paymentMethod, Authentication authentication){
        String userEmail = authentication.getName();
        String transactionRef = UUID.randomUUID().toString();

        User user = userService.getUser(userEmail);

        Transaction transaction = new Transaction(transactionRef, user.getId(), TransactionType.add_funds, BigDecimal.valueOf(paymentMethod.getAmount()), DateTime.getLocalDateTime(), TransactionStatus.pending);

        String response = flutterWaveService.bankTransferPayment(userEmail, paymentMethod.getAmount(), transactionRef);

        transactionService.add(transaction);

        return ResponseEntity.ok(
                Map.of(
                        "data", response,
                        "transactionRef", transactionRef
                )
        );
    }


}
