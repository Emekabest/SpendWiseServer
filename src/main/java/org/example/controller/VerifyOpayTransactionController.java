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

        try{

            String verifyOpayPaymentResponse = flutterWaveService.verifyOpayPayment(opay.getPaymentId());

            ObjectMapper mapper = new ObjectMapper();

            JsonNode root = mapper.readTree(verifyOpayPaymentResponse);

            JsonNode data = root.path("data");

            String status = data.path("status").asText();

            System.out.println("This is status::"+status);

            Map<String, Object> response = new HashMap<>();
            response.put("data", verifyOpayPaymentResponse);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
