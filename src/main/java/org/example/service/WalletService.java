package org.example.service;


import org.example.model.Wallet;
import org.example.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WalletService {


    @Autowired
    private WalletRepository walletRepository;

    public void add(Wallet wallet){

        walletRepository.save(wallet);
    }


    public Wallet get(int userId){
        Wallet wallet = new Wallet();

        Optional<Wallet> walletOptional = walletRepository.findByUserId(userId);
        if (walletOptional.isPresent()){

            wallet = walletOptional.get();
        }

        return wallet;
    }






}
