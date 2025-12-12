package com.example.expensetracker.controller;

import com.example.expensetracker.dto.CategorySpending;
import com.example.expensetracker.dto.SummaryResponse;
import com.example.expensetracker.security.CustomUserDetails;
import com.example.expensetracker.service.ReportService;
import jakarta.persistence.PreUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/spending-by-category")
    public List<CategorySpending> spendingByCategory(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ){
        return reportService.spendingByCategory(user.getId(), start, end);
    }

    @GetMapping("/summary")
    public SummaryResponse summary(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ){
        return reportService.summary(user.getId(), start, end);
    }
}
