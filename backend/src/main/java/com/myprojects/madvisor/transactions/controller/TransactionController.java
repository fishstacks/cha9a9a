package com.myprojects.madvisor.transactions.controller;

import com.myprojects.madvisor.security.user.CustomUserDetails;
import com.myprojects.madvisor.transactions.dto.TransactionDTO;
import com.myprojects.madvisor.transactions.dto.TransactionRequest;
import com.myprojects.madvisor.transactions.model.Transaction;
import com.myprojects.madvisor.transactions.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/recent")
    public Page<TransactionDTO> getTransactionsForUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Long userId = userDetails.getUserId();
        return transactionService.getTransactionsForUser(userId, page, size);
    }


    @PostMapping /*change to dto here*/
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody TransactionRequest request,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        Transaction transaction = transactionService.createTransaction(request, userId);
        return ResponseEntity.ok(transaction);

    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long transactionId,
            @RequestBody @Valid TransactionRequest transactionRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        Transaction updatedTransaction = transactionService.updateTransaction(transactionId, userId, transactionRequest);
        return ResponseEntity.ok(updatedTransaction);
    }


    @DeleteMapping
    public ResponseEntity<String> deleteTransaction(@PathVariable Long transactionId,
                                                @AuthenticationPrincipal CustomUserDetails userDetails){
        Long userId = userDetails.getUserId();
        boolean deleted = transactionService.deleteTransaction(transactionId, userId);
        if (deleted)
            return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/sum-of-incomes")
    public Map<String, Object> getIncomesForUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();

        BigDecimal incomes = transactionService.getSumOfIncomes(userId);
        List<Object[]> incomesByCategory = transactionService.getSumOfIncomesByCategory(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("sumOfIncomes", incomes);
        response.put("incomesByCategory", incomesByCategory);

        return response;
    }

    @GetMapping("/sum-of-expenses")
    public Map<String, Object> getExpensesForUser(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();

        BigDecimal expenses = transactionService.getSumOfExpenses(userId);
        List<Object[]> expensesByCategory = transactionService.getSumOfExpensesByCategory(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("sumOfExpenses", expenses);
        response.put("expensesByCategory", expensesByCategory);

        return response;
    }


}

