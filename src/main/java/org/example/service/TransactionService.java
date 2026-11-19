package org.example.service;

import org.example.enums.TransactionStatus;
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

        System.out.println("Searching for ref: " + transactionRef);


//        Transaction transaction = new Transaction();

        Optional<Transaction> transactionOptional = transactionRepository.findByTransactionRef(transactionRef);

        System.out.println("Found: " + transactionOptional.isPresent());
        if (transactionOptional.isPresent()){

            System.out.println("Amount: " + transactionOptional.get().getAmount());
//            transaction = transactionOptional.get();

        }

        return transactionOptional.orElseThrow(
                () -> new RuntimeException("Transaction not found")
        );
    }


    public List<Transaction> getAll(int userId){

        return transactionRepository.findByUserId(userId);
    }


    public void update(String transactionRef, TransactionStatus status){
            System.out.println("Transaction reached::-1");

            Transaction transaction = this.get(transactionRef);
            System.out.println("Transaction reached::-2");
            System.out.println("Transaction amount::"+ transaction.getAmount());

            transaction.setStatus(status);

            transactionRepository.save(transaction);
    }

}
