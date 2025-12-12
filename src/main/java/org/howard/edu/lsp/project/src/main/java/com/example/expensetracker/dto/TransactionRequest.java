package com.example.expensetracker.dto;

import com.example.expensetracker.domain.TransactionEntity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionRequest(
        @NotNull TransactionEntity.Type type,
        @NotNull @DecimalMin("0.01")BigDecimal amount,
        @NotBlank String category,
        String description,
        @NotNull LocalDateTime dateTime
){}
