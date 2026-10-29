package org.example.service;

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

    public Transaction get(String transactionRef){

        Transaction transaction = new Transaction();

        Optional<Transaction> transactionOptional = transactionRepository.findByTransactionRef(transactionRef);
        if (transactionOptional.isPresent()){
            transaction = transactionOptional.get();

        }

        return transaction;
    }


    public List<Transaction> getAll(int userId){

        return transactionRepository.findByUserId(userId);
    }


    public void update(Transaction updatedTransaction){

            Transaction transaction = this.get(updatedTransaction.getTransactionRef());
            transaction.setStatus(updatedTransaction.getStatus());

            transactionRepository.save(transaction);
    }

}
