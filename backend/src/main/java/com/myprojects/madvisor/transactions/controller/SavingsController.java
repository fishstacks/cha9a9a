package com.myprojects.madvisor.transactions.controller;

import com.myprojects.madvisor.security.user.CustomUserDetails;
import com.myprojects.madvisor.transactions.dto.TransactionRequest;
import com.myprojects.madvisor.transactions.model.Transaction;
import com.myprojects.madvisor.transactions.service.SavingsService;
import com.myprojects.madvisor.transactions.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/savings")
public class SavingsController {

    private final SavingsService savingsService;

    public SavingsController(SavingsService savingsService) {
        this.savingsService = savingsService;
    }

    @GetMapping("/realtime")
    public BigDecimal getRealtimeSavingsForUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        return savingsService.getAllSavings(userId);
    }


    @GetMapping
    public ResponseEntity<BigDecimal> getSavingsByMonth(
            @PathVariable int month,
            @PathVariable int year,
            @RequestBody @Valid TransactionRequest transactionRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        BigDecimal savingsByMonth = savingsService.getTotalFinalizedSavingsByMonth(userId, month, year);
        return ResponseEntity.ok(savingsByMonth);
    }
}
