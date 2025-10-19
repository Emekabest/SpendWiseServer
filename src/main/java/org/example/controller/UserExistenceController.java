package org.example.controller;

import lombok.ToString;
import org.example.model.EmailRequest;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserExistenceController {

    private final UserService userService;


    @Autowired
    public UserExistenceController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping({"/checkuser"})
    public String checkUser(@RequestBody EmailRequest emailRequest){

        try{
            String signupFeedbackMessage;

            if (!this.userService.isExistsByEmail(emailRequest.getEmail())){

                signupFeedbackMessage =  "Successful";
            } else {

                signupFeedbackMessage =  "Email is already registered";
            }

            return signupFeedbackMessage;
        } catch (Exception e) {
            throw new IllegalArgumentException("An error occurred:: " + e.getMessage());
        }



    }

}
