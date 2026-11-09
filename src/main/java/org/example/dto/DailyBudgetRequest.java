package org.example.dto;

import java.math.BigDecimal;

public record DailyBudgetRequest(BigDecimal amount, int durationDays){


}
