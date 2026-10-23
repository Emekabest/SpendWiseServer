package org.example.controller;


import org.example.model.Opay;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class VerifyOpayTransactionController {


    @PostMapping({"/verify-opay"})
    public ResponseEntity<?> request(@RequestBody Opay opay){


        Map<String, Object> response = new HashMap<>();
        response.put("status", "...");
        response.put("data", "{}");
        response.put("message", "data is coming");

        return ResponseEntity.ok(response);
    }

}
