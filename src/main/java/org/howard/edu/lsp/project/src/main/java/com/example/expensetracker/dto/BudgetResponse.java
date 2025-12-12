package com.example.expensetracker.dto;


import com.example.expensetracker.domain.BudgetEntity;

import java.math.BigDecimal;
import java.time.YearMonth;

public record BudgetResponse(
        Long id,
        String category,
        BigDecimal amountLimit,
        BudgetEntity.Period period,
        YearMonth periodStart
) {}
