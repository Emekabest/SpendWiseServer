package org.example.service;

import org.example.dto.DailyBudgetRequest;
import org.example.enums.BudgetStatus;
import org.example.enums.BudgetType;
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
    public Budget createDailyBudget(int userId, DailyBudgetRequest request){

        // Validate amount
        if (request.amount() == null ||
                request.amount().compareTo(BigDecimal.ZERO) <= 0){
            throw new RuntimeException("Invalid budget amount");
        }

        // Validate duration
        if (request.durationDays() < 1 ||
                request.durationDays() > 6) {
            throw new RuntimeException(
                    "Daily budget duration must be between 1 and 6 days"
            );
        }

        // Check for an existing active daily budget
        boolean alreadyExists = budgetRepository
                .findByUserIdAndTypeAndStatus(
                        userId, BudgetType.DAILY, BudgetStatus.ACTIVE
                ).isPresent();

        if (alreadyExists) {
            throw new RuntimeException(
                    "You already have an active daily budget"
            );
        }

        LocalDate startDate = LocalDate.now(
                ZoneId.of("Africa/Lagos")
        );

        LocalDate endDate = startDate.plusDays(
                request.durationDays() - 1
        );

        Budget budget = new Budget();

        budget.setUserId(userId);
        budget.setAmount(request.amount());
        budget.setDurationDays(request.durationDays());
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        budget.setType(BudgetType.DAILY);
        budget.setStatus(BudgetStatus.ACTIVE);


        // Calculate the overall end date based on budget type
        Budget savedBudget = budgetRepository.save(budget);

        // Generate all corresponding usage records
        createBudgetUsage(savedBudget);

        return savedBudget;
    }



    private void createBudgetUsage(Budget budget) {

        List<BudgetUsage> usages = new ArrayList<>();

        for (int i = 0; i < budget.getDurationDays(); i++) {

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

}
