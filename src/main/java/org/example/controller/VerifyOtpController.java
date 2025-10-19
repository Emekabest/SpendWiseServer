package org.example.controller;


import org.example.model.OtpRequest;
import org.example.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyOtpController {

    private OtpService otpService;

    @Autowired
    public VerifyOtpController(OtpService otpService){
        this.otpService = otpService;

    }


    @PostMapping({"/verifyotp"})
    public String verifyOtp(@RequestBody OtpRequest otpRequest){

        boolean isValid = otpService.verifyOtp(otpRequest.getEmail(), otpRequest.getOtp());

        if (isValid) {
            return "Successful";

        } else {
            return "Invalid or expired OTP.";

        }

    }

}
