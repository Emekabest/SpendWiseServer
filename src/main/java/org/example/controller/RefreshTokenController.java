package org.example.controller;

<<<<<<< HEAD
=======

>>>>>>> fixIssue
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

<<<<<<< HEAD
    private final AuthResponse authResponse;
=======
    private final AuthResponse authresponse;
>>>>>>> fixIssue

    private final JwtService jwtService;
    private final UserService userService;


    @Autowired
    public RefreshTokenController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
<<<<<<< HEAD
        this.authResponse = new AuthResponse();
=======
        this.authresponse = new AuthResponse();
>>>>>>> fixIssue
    }


    @PostMapping({"/refreshtoken"})
<<<<<<< HEAD
    public AuthResponse RefreshToken(@RequestBody AuthResponse authResponse){

        try{
            String refreshToken = authResponse.getRefreshToken();
=======
    public AuthResponse RefreshToken(@RequestBody AuthResponse request){

        try{
            String refreshToken = request.getRefreshToken();
>>>>>>> fixIssue

            String userEmail = jwtService.extractUsername(refreshToken);

            User user = userService.getUser(userEmail);

            boolean isTokenValid = jwtService.isTokenValid(refreshToken, user);

            if (!isTokenValid){
                throw new RuntimeException("Invalid Refresh Token");
            }

            String newAccessToken = jwtService.generateAccessToken(userEmail);

<<<<<<< HEAD
            return new AuthResponse(newAccessToken, refreshToken, "Successful");
=======




            authresponse.setAccessToken(newAccessToken);
            authresponse.setRefreshToken(refreshToken);
            authresponse.setMessage("Successful");


            return authresponse;
>>>>>>> fixIssue

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
