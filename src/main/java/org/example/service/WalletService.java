package org.example.service;


import org.example.enums.TransactionStatus;
import org.example.enums.TransactionType;
import org.example.exception.BudgetNotFoundException;
import org.example.model.Budget;
import org.example.model.BudgetUsage;
import org.example.model.Transaction;
import org.example.model.Wallet;
import org.example.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class WalletService {


    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BudgetService budgetService;

    public void add(Wallet wallet){

        walletRepository.save(wallet);
    }


    public Wallet get(int userId){
        Wallet wallet = new Wallet();

        Optional<Wallet> walletOptional = walletRepository.findByUserId(userId);
        if (walletOptional.isPresent()){

            wallet = walletOptional.get();
        }


        return wallet;
    }



    public void updateAvailableBalance(String transactionRef){
        Transaction transaction = transactionService.get(transactionRef);

        if (TransactionStatus.pending.equals(transaction.getStatus())){

            int userId = transaction.getUserId();

            BigDecimal transactionAmount = transaction.getAmount();
            TransactionType transactionType = transaction.getType();

            Wallet wallet = walletRepository.findByUserIdForUpdate(userId)
                    .orElseThrow(()-> new RuntimeException("Wallet not found"));

            BigDecimal availableBalance = wallet.getAvailableBalance();


            //add_funds
            if (TransactionType.add_funds.name().equals(transactionType.name())){
                 BigDecimal newAvailableBalance = availableBalance.add(transactionAmount);

                 wallet.setAvailableBalance(newAvailableBalance);

                 walletRepository.save(wallet);

            }

        }
    }



    @Transactional
    public String moveToSavings(int amountToSave, int userId){



        Wallet wallet = walletRepository.findByUserIdForUpdate(userId)
                .orElseThrow(()-> new RuntimeException("Wallet not found"));

        BigDecimal availableBalance = wallet.getAvailableBalance();
        BigDecimal savings = wallet.getSavings();



        if (availableBalance.compareTo(BigDecimal.valueOf(amountToSave)) < 0){
            return "Insufficient available balance";
        }


        availableBalance = availableBalance.subtract(BigDecimal.valueOf(amountToSave));
        savings = savings.add(BigDecimal.valueOf(amountToSave));


        wallet.setSavings(savings);
        wallet.setAvailableBalance(availableBalance);

        walletRepository.save(wallet);


        return "successful";
    }



    public BigDecimal getBudgetAllowance(int userId) {

        try {
            Budget budget = budgetService.getUserBudget(userId);

            LocalDate today = LocalDate.now(ZoneId.of("Africa/Lagos"));

            LocalDate periodStart;
            LocalDate periodEnd;

            switch (budget.getType()) {

                case DAILY -> {
                    periodStart = today;
                    periodEnd = today;
                }

                case WEEKLY -> {
                    periodStart = budget.getStartDate().plusWeeks(
                            ChronoUnit.WEEKS.between(
                                    budget.getStartDate(), today
                            )
                    );

                    periodEnd = periodStart.plusWeeks(1).minusDays(1);
                }

                case MONTHLY -> {
                    long months = ChronoUnit.MONTHS.between(
                            budget.getStartDate().withDayOfMonth(1),
                            today.withDayOfMonth(1)
                    );

                    periodStart = budget.getStartDate().plusMonths(months);
                    periodEnd = budget.getStartDate()
                            .plusMonths(months + 1)
                            .minusDays(1);
                }

                default -> throw new RuntimeException("Invalid budget type");
            }

            BudgetUsage usage = budgetService.getUserBudgetUsage(budget.getId(), periodStart, periodEnd);

            return budget.getAmount().subtract(usage.getAmountSpent());

        } catch (BudgetNotFoundException e) {
            return BigDecimal.ZERO;
        }
    }

}
