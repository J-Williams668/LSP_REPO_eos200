package com.example.expensetracker.service;

import com.example.expensetracker.domain.BudgetEntity;
import com.example.expensetracker.domain.TransactionEntity;
import com.example.expensetracker.domain.UserEntity;
import com.example.expensetracker.dto.TransactionAlertResponse;
import com.example.expensetracker.dto.TransactionRequest;
import com.example.expensetracker.dto.TransactionResponse;
import com.example.expensetracker.repository.BudgetRepository;
import com.example.expensetracker.repository.TransactionRepository;
import com.example.expensetracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, BudgetRepository budgetRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.budgetRepository = budgetRepository;
    }

    public TransactionResponse createTransaction(Long userId, TransactionRequest request) throws EntityNotFoundException {
        UserEntity user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("User not found"));

        TransactionEntity transaction = new TransactionEntity();
        transaction.setUser(user);
        transaction.setType(request.type());
        transaction.setAmount(request.amount());
        transaction.setCategory(request.category());
        transaction.setDescription(request.description());
        transaction.setDateTime(request.dateTime());

        TransactionEntity savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponse(
                savedTransaction.getId(),
                savedTransaction.getType(),
                savedTransaction.getAmount(),
                savedTransaction.getCategory(),
                savedTransaction.getDescription(),
                savedTransaction.getDateTime()
        );
    }

    public TransactionAlertResponse createBudgetCheckedTransaction(Long userId, TransactionRequest request){
        TransactionResponse transactionResponse = createTransaction(userId, request);
        List<BudgetEntity> budgets = budgetRepository.findByUserId(userId);

        boolean exceeded = false;
        String message = null;

        for(BudgetEntity budget : budgets){
            if(budget.getPeriod() == BudgetEntity.Period.MONTHLY){
                YearMonth yearMonth = budget.getPeriodStart();
                LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
                LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59);
                BigDecimal spending = transactionRepository
                        .findByUserIdAndDateTime(userId, start, end)
                        .stream()
                        .filter(t -> t.getType() == TransactionEntity.Type.EXPENSE)
                        .filter(t ->
                                budget.getCategory() == null || budget.getCategory().equalsIgnoreCase(t.getCategory()))
                        .map(TransactionEntity::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                if(spending.compareTo(budget.getAmountLimit()) > 0){
                    exceeded = true;
                    message = "Budget exceeded for " +
                            (budget.getCategory() != null ? budget.getCategory() : "overall spending")
                            + " in " + yearMonth;
                    break;
                }
            }
        }
        return new TransactionAlertResponse(transactionResponse, exceeded, message);
    }

    public List<TransactionResponse> listTransactions(Long userId, LocalDateTime start, LocalDateTime end){
        return transactionRepository.findByUserIdAndDateTime(userId, start, end)
                .stream()
                .map(t -> new TransactionResponse(
                        t.getId(),
                        t.getType(),
                        t.getAmount(),
                        t.getCategory(),
                        t.getDescription(),
                        t.getDateTime()
                ))
                .toList();
    }
}
