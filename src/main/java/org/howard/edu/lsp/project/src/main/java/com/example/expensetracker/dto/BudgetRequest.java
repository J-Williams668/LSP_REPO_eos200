package com.example.expensetracker.dto;

import com.example.expensetracker.domain.BudgetEntity;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.YearMonth;

public record BudgetRequest(
        @Nullable String category,
        @NotNull @DecimalMin("0.01") BigDecimal amountLimit,
        @NotNull BudgetEntity.Period period,
        @NotNull YearMonth periodStart
) {}
