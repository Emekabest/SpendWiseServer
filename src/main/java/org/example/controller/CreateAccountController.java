package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CreateAccountController {
    private final UserService userService;


    @Autowired
    public CreateAccountController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping({"/signup"})
    public String SignUp(@RequestBody User newUser){

        try {
            String signupFeedbackMessage;
            if (!this.userService.isExistsByEmail(newUser.getEmail())){
                this.userService.add(newUser);
                signupFeedbackMessage = "Successful";

            } else {
                signupFeedbackMessage = "Email is already registered";

            }



            return signupFeedbackMessage;
        } catch (Exception e) {
            throw new IllegalArgumentException("An error occurred:: " + e.getMessage());
        }

    }

}
