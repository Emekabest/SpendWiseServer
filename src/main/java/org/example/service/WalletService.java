package org.example.service;


import org.example.model.Wallet;
import org.example.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {


    @Autowired
    private WalletRepository walletRepository;

    public void add(Wallet wallet){

        walletRepository.save(wallet);
    }






}
