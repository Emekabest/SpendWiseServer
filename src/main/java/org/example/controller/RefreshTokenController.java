package org.example.controller;

import org.example.model.AuthResponse;
import org.example.model.User;
import org.example.service.JwtService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {

    private final AuthResponse authResponse;

    private final JwtService jwtService;
    private final UserService userService;


    @Autowired
    public RefreshTokenController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authResponse = new AuthResponse();
    }


    @PostMapping({"/refreshtoken"})
    public AuthResponse RefreshToken(@RequestBody AuthResponse authResponse){

        try{
            String refreshToken = authResponse.getRefreshToken();

            String userEmail = jwtService.extractUsername(refreshToken);

            User user = userService.getUser(userEmail);

            boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);

            if (!isTokenValid){
                throw new RuntimeException("Invalid Refresh Token");
            }

            String newAccessToken = jwtService.generateAccessToken(userEmail);

            return new AuthResponse(newAccessToken, refreshToken, "Successful");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
