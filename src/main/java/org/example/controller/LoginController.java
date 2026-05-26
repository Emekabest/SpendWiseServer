package org.example.controller;

import org.example.model.User;
import org.example.service.JwtService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {
    private final UserService userService;
    private final JwtService jwtService;


    @Autowired
    private PasswordEncoder passwordEncoder;




    @Autowired
    public LoginController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @PostMapping({"/signin"})
    public String Login(@RequestBody User loginReq) {
        try {
            Optional<User> userOptional = this.userService.get(loginReq.getEmail());

            if (userOptional.isEmpty()){

                return "Invalid email or password";
            } else {
                User user = (User)userOptional.get();
                if (passwordEncoder.matches(loginReq.getPin(),user.getPin())){

                    String token = jwtService.generateToken(user.getEmail());

                    System.out.println("This is the generated token: " + token);

                    return "Successful";
                }
                else{

                    return "Invalid email or password";
                }
            }
        } catch (Exception e) {

            throw new IllegalArgumentException("An error occurred:: " + e.getMessage());
        }
    }
}
