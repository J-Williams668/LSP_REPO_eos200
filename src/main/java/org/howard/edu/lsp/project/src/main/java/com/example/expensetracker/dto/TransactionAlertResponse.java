package com.example.expensetracker.dto;

public record TransactionAlertResponse(
        TransactionResponse transaction,
        boolean budgetExceeded,
        String message
) {}
