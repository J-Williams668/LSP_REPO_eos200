package com.example.expensetracker.dto;

import java.math.BigDecimal;

public record CategorySpending(
        String category,
        BigDecimal total
){}
