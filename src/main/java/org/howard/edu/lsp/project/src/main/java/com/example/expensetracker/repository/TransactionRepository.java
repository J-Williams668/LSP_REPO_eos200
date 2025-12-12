package com.example.expensetracker.repository;

import com.example.expensetracker.domain.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByUserIdAndDateTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);

    @Query("""
            SELECT t.category, SUM(t.amount)
            FROM TransactionEntity t
            WHERE t.user.id = :userId
                AND t.type = com.example.expensetracker.domain.TransactionEntity.Type.EXPENSE
                AND t.dateTime BETWEEN :start AND :end
            GROUP BY t.category
            """)
    List<Object[]> sumExpenseByCategory(Long userId, LocalDateTime start, LocalDateTime end);
}
