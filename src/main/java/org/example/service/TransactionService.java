package org.example.service;

import org.example.enums.TransactionStatus;
import org.example.model.Transaction;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void add(Transaction transaction){

        transactionRepository.save(transaction);
    }

    public Transaction get(String transactionRef){

        return transactionRepository.findByTransactionRef(transactionRef)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }


    public Page<Transaction> getTransactions(int userId, Pageable pageable) {

        return transactionRepository.findByUserId(userId, pageable);
    }


    public void update(String transactionRef, TransactionStatus status){

            Transaction transaction = this.get(transactionRef);

            transaction.setStatus(status);

            transactionRepository.save(transaction);
    }

}
