package org.example.repository;

import org.springframework.data.domain.Page;
import org.example.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    Optional<Transaction> findByTransactionRef(String transactionRef);

    Page<Transaction> findByUserId(int userId, Pageable pageable);

}
