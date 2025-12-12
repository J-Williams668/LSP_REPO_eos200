package com.example.expensetracker.controller;

import com.example.expensetracker.dto.BudgetRequest;
import com.example.expensetracker.dto.BudgetResponse;
import com.example.expensetracker.security.CustomUserDetails;
import com.example.expensetracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BudgetResponse create(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody BudgetRequest request
    ){
        return budgetService.createBudget(user.getId(), request);
    }

    @GetMapping
    public List<BudgetResponse> list(@AuthenticationPrincipal CustomUserDetails user){
        return budgetService.listBudgets(user.getId());
    }

    @PutMapping("/{id}")
    public BudgetResponse update(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id,
            @Valid @RequestBody BudgetRequest request
    ){
        return budgetService.updateBudget(user.getId(), id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long id
    ){
        budgetService.deleteBudget(user.getId(), id);
    }
}
