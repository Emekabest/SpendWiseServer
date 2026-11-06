package org.example.service;


import org.example.enums.TransactionStatus;
import org.example.enums.TransactionType;
import org.example.model.Transaction;
import org.example.model.Wallet;
import org.example.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {


    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionService transactionService;

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


    public void updateAvailableBalance(String transactionRef){
        Transaction transaction = transactionService.get(transactionRef);

        if (TransactionStatus.pending.equals(transaction.getStatus())){

            int userId = transaction.getUserId();

            BigDecimal transactionAmount = transaction.getAmount();
            TransactionType transactionType = transaction.getType();

            Wallet wallet = walletRepository.findByUserIdForMoveToSavingsUpdate(userId)
                    .orElseThrow(()-> new RuntimeException("Wallet not found"));

            BigDecimal availableBalance = wallet.getAvailableBalance();


            //add_funds
            if (TransactionType.add_funds.name().equals(transactionType.name())){
                 BigDecimal newAvailableBalance = availableBalance.add(transactionAmount);

                 wallet.setAvailableBalance(newAvailableBalance);

                 walletRepository.save(wallet);
            }

        }
    }



    @Transactional
    public String moveToSavings(int amountToSave, int userId){

        Wallet wallet = walletRepository.findByUserIdForMoveToSavingsUpdate(userId)
                .orElseThrow(()-> new RuntimeException("Wallet not found"));

        BigDecimal availableBalance = wallet.getAvailableBalance();
        BigDecimal savings = wallet.getSavings();

        if (availableBalance.compareTo(BigDecimal.valueOf(amountToSave)) < 0){
            return "Insufficient available balance";
        }


        availableBalance = availableBalance.subtract(BigDecimal.valueOf(amountToSave));
        savings = savings.add(BigDecimal.valueOf(amountToSave));


        wallet.setSavings(savings);
        wallet.setAvailableBalance(availableBalance);


        walletRepository.save(wallet);

        return "";
    }

}
