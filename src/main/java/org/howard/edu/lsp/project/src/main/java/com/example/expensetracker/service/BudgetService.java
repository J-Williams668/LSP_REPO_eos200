package com.example.expensetracker.service;

import com.example.expensetracker.domain.BudgetEntity;
import com.example.expensetracker.domain.UserEntity;
import com.example.expensetracker.dto.BudgetRequest;
import com.example.expensetracker.repository.BudgetRepository;
import com.example.expensetracker.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }

    public BudgetEntity createBudget(Long userId, BudgetRequest request){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        BudgetEntity budget = new BudgetEntity();
        budget.setUser(user);
        budget.setCategory(request.category());
        budget.setAmountLimit(request.amountLimit());
        budget.setPeriod(request.period());
        budget.setPeriodStart(request.periodStart());

        return budgetRepository.save(budget);
    }

    public List<BudgetEntity> listBudgets(Long userId){
        return budgetRepository.findByUserId(userId);
    }
}
