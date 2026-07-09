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

    private final AuthResponse authresponse;

    private final JwtService jwtService;
    private final UserService userService;


    @Autowired
    public RefreshTokenController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authresponse = new AuthResponse();
    }


    @PostMapping({"/refreshtoken"})
    public AuthResponse RefreshToken(@RequestBody AuthResponse request){

        try{
            String refreshToken = request.getRefreshToken();

            String userEmail = jwtService.extractUsername(refreshToken);

            User user = userService.getUser(userEmail);

            boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);

            if (!isTokenValid){
                throw new RuntimeException("Invalid Refresh Token");
            }

            String newAccessToken = jwtService.generateAccessToken(userEmail);





            authresponse.setAccessToken(newAccessToken);
            authresponse.setRefreshToken(refreshToken);
            authresponse.setMessage("Successful");


            return authresponse;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
