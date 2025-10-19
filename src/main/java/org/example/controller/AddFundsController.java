package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
public class AddFundsController{

    @Autowired
    public UserService userService;

    @PostMapping({"/addfunds"})
    public String addFunds(@RequestBody User addFundReq){
        try{
            Optional<User> userOptional = this.userService.get(addFundReq.getEmail());
            if (userOptional.isPresent()){
                User user = (User)userOptional.get();
                int userCurrentBalance = user.getBalance();
                user.setBalance(userCurrentBalance + addFundReq.getBalance());

                userService.update(user);
            }


            return "Successful";
        } catch (Exception e){

            throw new RuntimeException(e);
        }

    }
}
