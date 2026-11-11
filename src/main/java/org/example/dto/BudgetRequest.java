package org.example.dto;

import org.example.enums.BudgetType;

import java.math.BigDecimal;

public record BudgetRequest(BigDecimal amount, BudgetType type, int duration){


}
