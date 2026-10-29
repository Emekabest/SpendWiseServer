package org.example.controller;


import com.fasterxml.jackson.databind.JsonNode;
import org.example.enums.TransactionStatus;
import org.example.service.TransactionService;
import org.example.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlutterWaveWebhookController {

    @Value("${flw.webhook.secret.hash}")
    private String webhookSecretHash;

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;


    @PostMapping({"/api/webhooks/flutterwave"})
    public ResponseEntity<?> handleWebhook(@RequestHeader(value = "verif-hash", required = false)
                                           String signature, @RequestBody JsonNode payload  ){

        // 1. Authenticate the webhook
        if (signature == null ||
                !java.security.MessageDigest.isEqual(
                        signature.getBytes(java.nio.charset.StandardCharsets.UTF_8),
                        webhookSecretHash.getBytes(java.nio.charset.StandardCharsets.UTF_8))) {



            return ResponseEntity.status(401).build();
        }


        // 2. Read the event
        String event = payload.path("event").asText();

        JsonNode data = payload.path("data");

        String transactionRef = data.path("tx_ref").asText();
        String status = data.path("status").asText();



        if (TransactionStatus.successful.name().equals(status)){

             walletService.updateTotalBalance(transactionRef);

        }







        return ResponseEntity.ok().build();
    }

}
