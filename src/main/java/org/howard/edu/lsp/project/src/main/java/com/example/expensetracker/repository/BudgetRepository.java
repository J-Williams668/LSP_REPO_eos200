package com.example.expensetracker.repository;

import com.example.expensetracker.domain.BudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
    List<BudgetEntity> findByUserId(Long userId);
    Optional<BudgetEntity> findByIdAndUserId(Long id, Long userId);
}
