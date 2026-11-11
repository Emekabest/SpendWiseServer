package org.example.repository;

import org.example.model.BudgetUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BudgetUsageRepository
        extends JpaRepository<BudgetUsage, Long> {

    Optional<BudgetUsage> findByBudgetIdAndPeriodStartDateAndPeriodEndDate(
            Long budgetId,
            LocalDate periodStartDate,
            LocalDate periodEndDate
    );


    List<BudgetUsage> findByBudgetId(Long budgetId);
}