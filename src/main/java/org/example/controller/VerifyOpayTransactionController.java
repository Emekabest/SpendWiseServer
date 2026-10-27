package org.example.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.enums.TransactionStatus;
import org.example.model.Opay;
import org.example.model.Transaction;
import org.example.service.FlutterWaveService;
import org.example.service.TransactionService;
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


    @PostMapping({"/verify-payment/opay"})
    public ResponseEntity<?> request(@RequestBody Opay opay){

        Map<String, Object> response =  new HashMap<>();

        try{
            String verifyOpayPaymentResponse = flutterWaveService.verifyOpayPayment(opay.getPaymentId());
            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(verifyOpayPaymentResponse);

            JsonNode data = root.path("data");

            String paymentStatus = data.path("status").asText();


            if (TransactionStatus.failed.toString().equals(paymentStatus)){

                Transaction updatedTransaction = new Transaction();
                updatedTransaction.setTransactionRef(opay.getTransactionRef());
                updatedTransaction.setStatus(TransactionStatus.failed);

                transactionService.update(updatedTransaction);

                response.put("payment_status", TransactionStatus.failed);
                return ResponseEntity.ok(response);
            }

            if (TransactionStatus.successful.toString().equals(paymentStatus)){

                Transaction updatedTransaction = new Transaction();
                updatedTransaction.setTransactionRef(opay.getTransactionRef());
                updatedTransaction.setStatus(TransactionStatus.successful);

                transactionService.update(updatedTransaction);

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
