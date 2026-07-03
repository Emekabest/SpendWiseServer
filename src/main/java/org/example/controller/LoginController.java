package org.example.controller;

import org.example.model.Login;
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

    private final Login login;


    @Autowired
    private PasswordEncoder passwordEncoder;




    @Autowired
    public LoginController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;

        this.login = new Login();
    }


    @PostMapping({"/signin"})
    public Login Login(@RequestBody User loginReq) {
        try {
            Optional<User> userOptional = this.userService.get(loginReq.getEmail());

            if (userOptional.isEmpty()){

                login.setMessage("Invalid email or password");

                return login;
            } else {
                User user = (User)userOptional.get();
                if (passwordEncoder.matches(loginReq.getPin(),user.getPin())){

                    login.setAccessToken(jwtService.generateAccessToken(user.getEmail()));
                    login.setRefreshToken(jwtService.generateRefreshToken(user.getEmail()));


                    login.setMessage("Successful");

                    return login;
                }
                else{
                    login.setMessage("Invalid email or password");

                    return login;
                }
            }
        } catch (Exception e) {

            throw new IllegalArgumentException("An error occurred:: " + e.getMessage());
        }
    }
}
