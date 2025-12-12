package com.example.expensetracker.dto;

import com.example.expensetracker.domain.TransactionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        TransactionEntity.Type type,
        BigDecimal amount,
        String category,
        String description,
        LocalDateTime dateTime
) {}
