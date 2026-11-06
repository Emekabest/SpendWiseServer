package org.example.controller;

import org.example.dto.MoveToSavingsRecord;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MoveToSavingsController {


    @Autowired
    private UserService userService;

    @Autowired
    private WalletService walletService;


    @PostMapping({"/api/v1/move-to-savings"})
    public ResponseEntity<?> move(@RequestBody MoveToSavingsRecord moveToSavingsRecord, Authentication authentication){

        try{
            int amountToSave = moveToSavingsRecord.amountToSave();

            System.out.println("AmountToSave::"+amountToSave);

            String userEmail = authentication.getName();
            User user = userService.getUser(userEmail);
            int userId = user.getId();


            String moveToSavingsResponse = walletService.moveToSavings(amountToSave, userId);


            Map<String, Object> response =  new HashMap<>();

            response.put("status", 200);
            response.put("message", moveToSavingsResponse);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }

    }


}
