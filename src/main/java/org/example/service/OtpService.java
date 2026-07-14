package org.example.service;

import jakarta.transaction.Transactional;
import org.example.model.MyEmail;
import org.example.model.UserOtp;
import org.example.repository.UserOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
public class OtpService {

    private EmailService emailService;

    private UserOtpRepository userOtpRepository;


    @Autowired
    public OtpService(EmailService emailService, UserOtpRepository userOtpRepository){
        this.emailService = emailService;
        this.userOtpRepository = userOtpRepository;

    }


    public void add(UserOtp userOtp){

        userOtpRepository.save(userOtp);

    }



    public String sendOtp(String otp, String to){

        MyEmail myEmail = new MyEmail();
        myEmail.setFrom("onboarding@resend.dev");
        myEmail.setSubject("SpendWise Signup OTP Code");
        myEmail.setMessage("Your OTP code is: " + otp);
        myEmail.setTo(to);


        try {
            System.out.println("Otp about to be sent");

           return emailService.sendEmail(myEmail);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public boolean verifyOtp(String email, String otp){
        Optional<UserOtp> recentOtpOptional = userOtpRepository.findTopByEmailOrderByCreatedTimeDesc(email);

        if(recentOtpOptional.isEmpty()){

            return false;
        }

        UserOtp userOtp = recentOtpOptional.get();

        // Check expiry
        if (LocalDateTime.now().isAfter(userOtp.getExpiryTime())) {
            return false; // OTP expired
        }


        //Check match
        return userOtp.getOtp().equals(otp);
    }

}
