package org.example.controller;


import com.fasterxml.jackson.databind.JsonNode;
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

        String txRef = data.path("tx_ref").asText();
        String status = data.path("status").asText();

        System.out.println("Webhook event: " + event);
        System.out.println("Transaction reference: " + txRef);
        System.out.println("Payment status: " + status);


        return ResponseEntity.ok().build();
    }

}
