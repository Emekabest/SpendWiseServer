package org.example.service;


import org.example.exception.WithdrawPinNotFoundException;
import org.example.model.WithdrawPin;
import org.example.repository.WithdrawPinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WithdrawPinService {


    @Autowired
    private WithdrawPinRepository withdrawPinRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void add(WithdrawPin withdrawPin){

        withdrawPinRepository.save(withdrawPin);
    }


    public WithdrawPin get(int userId){

        return withdrawPinRepository.findByUserId(userId)
                .orElseThrow(() -> new WithdrawPinNotFoundException(
                        "You have not created a Withdrawal PIN"
                ));
    }


    public String verify(String pin, int userId){

        WithdrawPin withdrawPin = withdrawPinRepository.findByUserId(userId)
                .orElseThrow(() -> new WithdrawPinNotFoundException(
                "You have not created a Withdrawal PIN"));

        if (passwordEncoder.matches(pin, withdrawPin.getPinHash())){

            return "successful";
        }
        else {

            return "failed";
        }

    }

}
