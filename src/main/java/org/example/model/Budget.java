package org.example.model;

import jakarta.persistence.*;
import org.example.enums.BudgetStatus;
import org.example.enums.BudgetType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int userId;

    private BigDecimal amount;

    private int durationDays;
    private int durationWeeks;
    private int durationMonths;


    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private BudgetType type;


    @Enumerated(EnumType.STRING)
    private BudgetStatus status; // ACTIVE, EXPIRED


    public long getId() {
        return id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setStatus(BudgetStatus status) {
        this.status = status;
    }

    public BudgetStatus getStatus() {
        return status;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setType(BudgetType type) {
        this.type = type;
    }


    public BudgetType getType() {
        return type;
    }

    public void setDuration(int durationDays) {
        this.durationDays = durationDays;
    }

    public int getDuration() {
        return durationDays;
    }


    public void setDurationWeeks(int durationWeeks){
        this.durationWeeks = durationWeeks;
    }
    public int getDurationWeeks() {
        return durationWeeks;
    }


    public void setDurationMonths(int durationMonths) {
        this.durationMonths = durationMonths;
    }
    public int getDurationMonths() {
        return durationMonths;
    }



    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

}