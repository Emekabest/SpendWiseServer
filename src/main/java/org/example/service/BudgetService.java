package org.example.service;

import org.example.dto.BudgetRequest;
import org.example.enums.BudgetStatus;
import org.example.exception.BudgetNotFoundException;
import org.example.model.Budget;
import org.example.model.BudgetUsage;
import org.example.repository.BudgetRepository;
import org.example.repository.BudgetUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    private final BudgetUsageRepository budgetUsageRepository;


    @Autowired
    public BudgetService(BudgetRepository budgetRepository, BudgetUsageRepository budgetUsageRepository){
        this.budgetRepository = budgetRepository;
        this.budgetUsageRepository = budgetUsageRepository;
    }


    @Transactional
    public Budget createBudget(int userId, BudgetRequest request) {

        // Validate amount
        if (request.amount() == null ||
                request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invalid budget amount");
        }

        // Validate duration
        if (request.duration() < 1) {
            throw new RuntimeException("Invalid budget duration");
        }

        // Validate duration based on budget type
        switch (request.type()) {
            case DAILY -> {
                if (request.duration() > 6) {
                    throw new RuntimeException(
                            "Daily budget duration must be between 1 and 6 days"
                    );
                }
            }

            case WEEKLY -> {
                if (request.duration() > 3) {
                    throw new RuntimeException(
                            "Weekly budget duration must be between 1 and 3 weeks"
                    );
                }
            }

            case MONTHLY -> {
                if (request.duration() > 11) {
                    throw new RuntimeException(
                            "Monthly budget duration must be between 1 and 11 months"
                    );
                }
            }
        }

        // Check for existing active budget
        boolean alreadyExists = budgetRepository
                .findByUserIdAndStatus(userId, BudgetStatus.ACTIVE)
                .isPresent();

        if (alreadyExists) {
            throw new RuntimeException("You already have an active budget");
        }


        LocalDate startDate = LocalDate.now(
                ZoneId.of("Africa/Lagos")
        );


        // Calculate end date based on budget type
        LocalDate endDate = switch (request.type()) {
            case DAILY -> startDate.plusDays(request.duration() - 1);
            case WEEKLY -> startDate.plusWeeks(request.duration()).minusDays(1);
            case MONTHLY -> startDate.plusMonths(request.duration()).minusDays(1);
        };

        Budget budget = new Budget();

        budget.setUserId(userId);
        budget.setAmount(request.amount());
        budget.setDuration(request.duration());
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setType(request.type());
        budget.setStatus(BudgetStatus.ACTIVE);

        Budget savedBudget = budgetRepository.save(budget);

        // Generate usage records
        createBudgetUsage(savedBudget);

        return savedBudget;
    }


    private void createBudgetUsage(Budget budget) {

        List<BudgetUsage> usages = new ArrayList<>();


        for (int i = 0; i < budget.getDuration(); i++) {

            LocalDate periodStart;
            LocalDate periodEnd;

            switch (budget.getType()) {

                case DAILY -> {
                    periodStart = budget.getStartDate().plusDays(i);
                    periodEnd = periodStart;
                }

                case WEEKLY -> {
                    periodStart = budget.getStartDate().plusWeeks(i);
                    periodEnd = periodStart.plusWeeks(1).minusDays(1);
                }

                case MONTHLY -> {
                    periodStart = budget.getStartDate().plusMonths(i);
                    periodEnd = budget.getStartDate()
                            .plusMonths(i + 1)
                            .minusDays(1);
                }

                default -> throw new RuntimeException("Invalid budget type");
            }

            BudgetUsage usage = new BudgetUsage();

            usage.setBudgetId(budget.getId());
            usage.setPeriodStartDate(periodStart);
            usage.setPeriodEndDate(periodEnd);
            usage.setAmountSpent(BigDecimal.ZERO);

            usages.add(usage);
        }

        budgetUsageRepository.saveAll(usages);
    }


    public Budget getUserBudget(int userId){

        return budgetRepository
                .findByUserIdAndStatus(userId, BudgetStatus.ACTIVE)
                .orElseThrow(() -> new BudgetNotFoundException("Budget not found"));
    }


    public BudgetUsage getUserBudgetUsage(long budgetId, LocalDate periodStartDate, LocalDate periodEndDate){

        return budgetUsageRepository
                .findByBudgetIdAndPeriodStartDateAndPeriodEndDate(budgetId, periodStartDate, periodEndDate )
                .orElseThrow(() -> new RuntimeException("Budget usage not found"));
    }

}
