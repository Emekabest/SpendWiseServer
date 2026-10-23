package org.example.controller;


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


    @PostMapping({"/verify-opay"})
    public ResponseEntity<?> request(@RequestBody Opay opay){


        String verifyOpayPaymentResponse = flutterWaveService.verifyOpayPayment(opay.getTransactionId());


        Map<String, Object> response = new HashMap<>();
        response.put("data", verifyOpayPaymentResponse);

        return ResponseEntity.ok(response);
    }

}
