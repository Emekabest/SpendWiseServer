package org.example.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Opay;
import org.example.service.FlutterWaveService;
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


    @PostMapping({"/verify-payment/opay"})
    public ResponseEntity<?> request(@RequestBody Opay opay){

        Map<String, Object> response =  new HashMap<>();

        try{
                String verifyOpayPaymentResponse = flutterWaveService.verifyOpayPayment(opay.getPaymentId());
                ObjectMapper mapper = new ObjectMapper();

                JsonNode root = mapper.readTree(verifyOpayPaymentResponse);

                JsonNode data = root.path("data");

                String paymentStatus = data.path("status").asText();

                if ("failed".equals(paymentStatus)){

                    response.put("payment_status", "failed");

                    return ResponseEntity.ok(response);
                }

                if ("successful".equals(paymentStatus)){

                    response.put("payment_status", "successful");

                    return ResponseEntity.ok(response);
                }


            response.put("payment_status", "pending");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
