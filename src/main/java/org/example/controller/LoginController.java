package org.example.controller;

import org.example.model.AuthResponse;
import org.example.model.User;
import org.example.service.JwtService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class LoginController {
    private final UserService userService;
    private final JwtService jwtService;

    private final AuthResponse authResponse;
    


    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    public LoginController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;

        this.authResponse = new AuthResponse();
    }


    @PostMapping({"/api/v1/login"})
    public ResponseEntity<?> Login(@RequestBody User loginReq){
        Map<String, Object> response =  new HashMap<>();

        try {
            Optional<User> userOptional = this.userService.get(loginReq.getEmail());

            if (userOptional.isEmpty()){

                authResponse.setMessage("Invalid email or password");


                response.put("status", 403);
                response.put("message", authResponse.getMessage());

                return ResponseEntity.ok(response);
            } else {
                User user = (User)userOptional.get();
                if (passwordEncoder.matches(loginReq.getPin(),user.getPin())){

                    authResponse.setAccessToken(jwtService.generateAccessToken(user.getEmail()));
                    authResponse.setRefreshToken(jwtService.generateRefreshToken(user.getEmail()));
                    authResponse.setMessage("successful");


                    response.put("status", 200);
                    response.put("access_token", authResponse.getAccessToken());
                    response.put("refresh_token", authResponse.getRefreshToken());
                    response.put("message", authResponse.getMessage());

                    return ResponseEntity.ok(response);
                }
                else{
                    authResponse.setMessage("Invalid email or password");

                    response.put("status", 403);
                    response.put("message", authResponse.getMessage());

                    return ResponseEntity.ok(response);
                }
            }
        } catch (Exception e) {


            throw new IllegalArgumentException("An error occurred:: " + e.getMessage());
        }
    }
}
