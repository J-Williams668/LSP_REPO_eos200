package com.example.expensetracker.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Table(name="budgets")
public class BudgetEntity {

    public enum Period{
        MONTHLY, WEEKLY, YEARLY
    }

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private UserEntity user;

    private String category;

    @Column(nullable = false, precision=19, scale=2)
    private BigDecimal amountLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Period period;

    @Column(nullable = false)
    private YearMonth periodStart;

    //Getters//
    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmountLimit() {
        return amountLimit;
    }

    public Period getPeriod() {
        return period;
    }

    public YearMonth getPeriodStart() {
        return periodStart;
    }

    //Setters//
    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmountLimit(BigDecimal amountLimit) {
        this.amountLimit = amountLimit;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public void setPeriodStart(YearMonth periodStart) {
        this.periodStart = periodStart;
    }
}
