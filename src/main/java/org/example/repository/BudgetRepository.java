package org.example.repository;

import org.example.enums.BudgetStatus;
import org.example.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {



    Optional<Budget> findByUserIdAndStatus(int userId, BudgetStatus status );

}