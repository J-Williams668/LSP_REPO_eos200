package com.example.expensetracker.service;

import com.example.expensetracker.domain.TransactionEntity;
import com.example.expensetracker.dto.CategorySpending;
import com.example.expensetracker.dto.SummaryResponse;
import com.example.expensetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {
    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<CategorySpending> spendingByCategory(Long userId, LocalDateTime start, LocalDateTime end){
        return transactionRepository.sumExpenseByCategory(userId, start, end)
                .stream()
                .map(row ->
                        new CategorySpending((String) row[0], (BigDecimal) row[1]))
                .toList();
    }

    public SummaryResponse summary(Long userId, LocalDateTime start, LocalDateTime end){
        List<TransactionEntity> transactions = transactionRepository.findByUserIdAndDateTimeBetween(userId, start, end);

        BigDecimal income = transactions.stream()
                .filter(t -> t.getType() == TransactionEntity.Type.INCOME)
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expenses = transactions.stream()
                .filter(t -> t.getType() == TransactionEntity.Type.EXPENSE)
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new SummaryResponse(
                income,
                expenses,
                income.subtract(expenses)
        );
    }
}