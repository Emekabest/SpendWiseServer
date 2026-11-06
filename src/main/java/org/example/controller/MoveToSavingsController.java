package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.example.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoveToSavingsController {


    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;


    @PostMapping({""})
    public ResponseEntity<?> request(@RequestBody int amountToSave, Authentication authentication){

        String userEmail = authentication.getName();
        User user = userService.getUser(userEmail);
        int userId = user.getId();


        walletService.moveToSavings(amountToSave, userId);


        return ResponseEntity.ok().build();
    }


}
