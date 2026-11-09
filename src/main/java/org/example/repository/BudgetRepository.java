package org.example.repository;

import org.example.enums.BudgetStatus;
import org.example.enums.BudgetType;
import org.example.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    Optional<Budget> findByUserIdAndTypeAndStatus(int userId, BudgetType type, BudgetStatus status );

}