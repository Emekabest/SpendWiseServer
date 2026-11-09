package org.example.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table( name = "budget_usage", uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "budget_id",
                                "period_start_date",
                                "period_end_date"
                        }
                )
        }
)
public class BudgetUsage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "budget_id", nullable = false)
    private Long budgetId;

    @Column(name = "period_start_date", nullable = false)
    private LocalDate periodStartDate;

    @Column(name = "period_end_date", nullable = false)
    private LocalDate periodEndDate;

    @Column(name = "amount_spent", nullable = false)
    private BigDecimal amountSpent = BigDecimal.ZERO;




    public Long getId() {
        return id;
    }


    public void setBudgetId(Long budgetId){
        this.budgetId = budgetId;
    }
    public Long getBudgetId() {
        return budgetId;
    }

    public void setPeriodStartDate(LocalDate periodStartDate) {
        this.periodStartDate = periodStartDate;
    }
    public LocalDate getPeriodStartDate() {
        return periodStartDate;
    }

    public void setPeriodEndDate(LocalDate periodEndDate) {
        this.periodEndDate = periodEndDate;
    }
    public LocalDate getPeriodEndDate() {
        return periodEndDate;
    }


    public void setAmountSpent(BigDecimal amountSpent) {
        this.amountSpent = amountSpent;
    }

    public BigDecimal getAmountSpent() {
        return amountSpent;
    }
}