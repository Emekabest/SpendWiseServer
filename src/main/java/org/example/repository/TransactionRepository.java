package org.example.repository;

import org.example.model.Notification;
import org.example.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUserEmail(String userEmail);

    Optional<Transaction> findByWithdrawTxnId(String withdrawTxnId);

}
