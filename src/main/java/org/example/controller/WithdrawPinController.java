package org.example.controller;


import org.example.dto.WithdrawPinRequest;
import org.example.exception.WithdrawPinNotFoundException;
import org.example.model.WithdrawPin;
import org.example.service.UserService;
import org.example.service.WithdrawPinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WithdrawPinController {

    @Autowired
    private UserService userService;

    @Autowired
    private WithdrawPinService withdrawPinService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping("/api/v1/withdrawpin/create")
    public String createWithdrawalPin(@RequestBody WithdrawPinRequest withdrawPinRequest, Authentication authentication){

        try {
            if (withdrawPinRequest.pin() == null || !withdrawPinRequest.pin().matches("\\d{4}")){
                return "Invalid Pin";
            }

            String userEmail = authentication.getName();
            int userId = userService.getUser(userEmail).getId();

            String hashedPin = passwordEncoder.encode(withdrawPinRequest.pin());

            WithdrawPin withdrawPin = new WithdrawPin();
            withdrawPin.setUserId(userId);
            withdrawPin.setPinHash(hashedPin);
            withdrawPin.setCreatedAt(LocalDateTime.now());
            withdrawPin.setUpdatedAt(LocalDateTime.now());

            withdrawPinService.add(withdrawPin);

            return "successful";
        } catch (Exception e){

            throw new RuntimeException(e);
        }
    }


    @GetMapping("/api/v1/withdrawpin/check")
    public ResponseEntity<?> checkWithdrawPin(Authentication authentication){

        try {

            String userEmail = authentication.getName();
            int userId = userService.getUser(userEmail).getId();

            WithdrawPin withdrawPin = withdrawPinService.get(userId);

            Map<String, Object> response =  new HashMap<>();

            response.put("status", "200");
            response.put("message", "successful");

            return ResponseEntity.ok(response);

        } catch (WithdrawPinNotFoundException e) {

            Map<String, Object> response =  new HashMap<>();

            response.put("status", "401");
            response.put("message", "not_found");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

}
