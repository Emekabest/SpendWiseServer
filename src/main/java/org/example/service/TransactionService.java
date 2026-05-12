package org.example.service;

import org.example.model.Notification;
import org.example.model.Transaction;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void add(Transaction transaction){

        transactionRepository.save(transaction);
    }


    public List<Transaction> get(String userEmail){

        return transactionRepository.findByUserEmail(userEmail);
    }


    public void update(Transaction updatedTransaction){
        Optional<Transaction> transactionOptional = transactionRepository.findByWithdrawTxnId(updatedTransaction.getWithdrawTxnId());

        if (transactionOptional.isPresent()){
            Transaction transaction = transactionOptional.get();
            transaction.setStatus(updatedTransaction.getStatus());

            transactionRepository.save(transaction);
        }
    }

}
