package com.example.expensetracker.service;

import com.example.expensetracker.domain.BudgetEntity;
import com.example.expensetracker.domain.UserEntity;
import com.example.expensetracker.dto.BudgetRequest;
import com.example.expensetracker.dto.BudgetResponse;
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

    public BudgetResponse createBudget(Long userId, BudgetRequest request){
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        BudgetEntity budget = new BudgetEntity();
        budget.setUser(user);
        budget.setCategory(request.category());
        budget.setAmountLimit(request.amountLimit());
        budget.setPeriod(request.period());
        budget.setPeriodStart(request.periodStart());

        BudgetEntity savedBudget = budgetRepository.save(budget);
        return toResponse(savedBudget);
    }

    public List<BudgetResponse> listBudgets(Long userId){
        return budgetRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public BudgetResponse updateBudget(Long userId, Long budgetId, BudgetRequest request) throws EntityNotFoundException{
        BudgetEntity budget = budgetRepository.findByIdAndUserId(budgetId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));

        budget.setCategory(request.category());
        budget.setAmountLimit(request.amountLimit());
        budget.setPeriod(request.period());
        budget.setPeriodStart(request.periodStart());

        BudgetEntity savedBudget = budgetRepository.save(budget);
        return toResponse(savedBudget);
    }

    public void deleteBudget(Long userId, Long budgetId) throws EntityNotFoundException{
        BudgetEntity budget = budgetRepository.findByIdAndUserId(budgetId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));

        budgetRepository.delete(budget);
    }

    private BudgetResponse toResponse(BudgetEntity b){
        return new BudgetResponse(
                b.getId(),
                b.getCategory(),
                b.getAmountLimit(),
                b.getPeriod(),
                b.getPeriodStart()
        );
    }
}
