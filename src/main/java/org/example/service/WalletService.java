package org.example.service;


import org.example.enums.TransactionStatus;
import org.example.enums.TransactionType;
import org.example.model.Transaction;
import org.example.model.Wallet;
import org.example.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public void updateTotalBalance(String transactionRef){
        Transaction transaction = transactionService.get(transactionRef);

        if (TransactionStatus.pending.equals(transaction.getStatus())){

            int userId = transaction.getUserId();

            BigDecimal transactionAmount = transaction.getAmount();
            TransactionType transactionType = transaction.getType();

            Wallet wallet = this.get(userId);

            BigDecimal totalBalance = wallet.getTotalBalance();


            //add_funds
            if (TransactionType.add_funds.name().equals(transactionType.name())){
                 BigDecimal newTotalBalance = totalBalance.add(transactionAmount);

                 wallet.setTotalBalance(newTotalBalance);

                 walletRepository.save(wallet);
            }

        }
    }

}
