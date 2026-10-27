package org.example.controller;


import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping({"/api/v1/users/me"})
    public ResponseEntity<?> user(Authentication authentication){

        String email = authentication.getName();

        System.out.println("This is my email::"+email);


        try{
            User user = userService.getUser(email);


            Map<String, Object> response =  new HashMap<>();

            response.put("status", 200);
//            response.put("firstname", user.getFirstname());
//            response.put("lastname", user.getLastname());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
