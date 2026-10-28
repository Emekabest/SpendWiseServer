package org.example.controller;

import org.example.model.Wallet;
import org.example.service.UserService;
import org.example.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class WalletController {

    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/api/v1/wallet")
    public ResponseEntity<?> getwallet(Authentication authentication){
        try {

            String userEmail = authentication.getName();
            int userId = userService.getUser(userEmail).getId();


            Wallet wallet = walletService.get(userId);

            Map<String, Object> response =  new HashMap<>();
            response.put("total_balance", wallet.getTotalBalance());
            response.put("access_balance", wallet.getAccessBalance());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
