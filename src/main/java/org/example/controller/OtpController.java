package org.example.controller;

import org.example.OtpGenerator;
import org.example.model.EmailRequest;
import org.example.model.UserOtp;
import org.example.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
public class OtpController {

    public final OtpService otpService;

    @Autowired
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }



    @PostMapping({"/sendotp"})
    public String sendOtp(@RequestBody EmailRequest emailRequest){
        int otpExpiryTimeMinutes = 3;

        try{
            String otp = OtpGenerator.generateOtp(4);

            UserOtp userOtp = new UserOtp();
            userOtp.setEmail(emailRequest.getEmail());
            userOtp.setOtp(otp);
            userOtp.setCreatedTime(LocalDateTime.now());
            userOtp.setExpiryTime(LocalDateTime.now().plusMinutes(otpExpiryTimeMinutes));

            otpService.add(userOtp);


            String otpResponse =  otpService.sendOtp(otp, emailRequest.getEmail());

            return "Successful";
        }
        catch (Exception e) {
            throw new IllegalArgumentException("An error occurred:: " + e.getMessage());

        }
    }
}
