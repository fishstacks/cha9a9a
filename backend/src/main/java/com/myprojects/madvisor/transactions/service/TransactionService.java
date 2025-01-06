package com.myprojects.madvisor.transactions.service;

import com.myprojects.madvisor.security.user.User;
import com.myprojects.madvisor.security.user.UserRepository;
import com.myprojects.madvisor.transactions.dto.TransactionDTO;
import com.myprojects.madvisor.transactions.dto.TransactionRequest;
import com.myprojects.madvisor.transactions.model.Category;
import com.myprojects.madvisor.transactions.model.Transaction;
import com.myprojects.madvisor.transactions.repository.CategoryRepository;
import com.myprojects.madvisor.transactions.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository,
                              CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<TransactionDTO> getTransactionsForUser(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Transaction> transactionPage = transactionRepository.findByUserId(userId, pageable);

        return transactionPage.map(transaction -> new TransactionDTO(
                transaction.getId(),
                transaction.getCategory(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDate(),
                transaction.getDescription(),
                transaction.getIsFixed()
        ));
    }
    public Transaction createTransaction(TransactionRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findByCategoryId(request.getCategoryId());

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setDate(request.getDate());
        transaction.setIsFixed(request.getIsFixed());
        transaction.setFrequency(request.getFrequency());
        transaction.setDescription(request.getDescription());
        transactionRepository.save(transaction);

        return transaction;
    }

    public Transaction updateTransaction(Long transactionId, Long userId, TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findByIdAndUserId(transactionId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

        Category category = categoryRepository.findByCategoryId(transactionRequest.getCategoryId());

        transaction.setAmount(transactionRequest.getAmount());
        transaction.setType(transactionRequest.getType());
        transaction.setDate(transactionRequest.getDate());
        transaction.setDescription(transactionRequest.getDescription());
        transaction.setCategory(category);
        transaction.setIsFixed(transactionRequest.getIsFixed());
        transaction.setFrequency(transactionRequest.getFrequency());

        transactionRepository.save(transaction);

        return transaction;
    }

    public boolean deleteTransaction(Long transactionId, Long userId) {

        try {
            transactionRepository.deleteByIdAndUserId(transactionId, userId);
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public BigDecimal getSumOfIncomes(Long userId) {
        BigDecimal incomes = transactionRepository.sumAllIncomes(userId);

        return incomes;
    }

    public List<Object[]> getSumOfIncomesByCategory(Long userId) {
        List<Object[]> incomesByCategory = transactionRepository.sumAllIncomesByCategory(userId);

        return incomesByCategory;
    }

    public BigDecimal getSumOfExpenses(Long userId) {
        BigDecimal expenses = transactionRepository.sumAllExpenses(userId);

        return expenses;
    }
    public List<Object[]> getSumOfExpensesByCategory(Long userId) {
        List<Object[]> expensesByCategory = transactionRepository.sumAllExpensesByCategory(userId);

        return expensesByCategory;
    }


}
