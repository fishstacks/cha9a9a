package com.myprojects.madvisor.transactions.service;

import com.myprojects.madvisor.security.user.User;
import com.myprojects.madvisor.security.user.UserRepository;
import com.myprojects.madvisor.transactions.model.Savings;
import com.myprojects.madvisor.transactions.repository.SavingsRepository;
import com.myprojects.madvisor.transactions.repository.TransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SavingsService {

    private final UserRepository userRepository;
    private final SavingsRepository savingsRepository;
    private final TransactionRepository transactionRepository;

    public SavingsService(UserRepository userRepository, SavingsRepository savingsRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.savingsRepository = savingsRepository;
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getTotalFinalizedSavings(Long userId) {
        return savingsRepository.sumFinalAmountByUserId(userId);
    }

    public BigDecimal getTotalFinalizedSavingsByMonth(Long userId, int month, int year) {
        return savingsRepository.sumFinalAmountByUserIdAndMonthAndYear(userId, month, year);
    }

    public BigDecimal getAllSavings(Long userId) {
        return transactionRepository.sumAllIncomes(userId).subtract(transactionRepository.sumAllExpenses(userId));

    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void finalizeMonthlySavings() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            BigDecimal lastMonthSavings =
                    (transactionRepository.sumIncomesByMonthAndYear(user.getUserId(), (LocalDate.now().minusMonths(1).getMonthValue()), LocalDate.now().minusMonths(1).getYear()))
                            .subtract(transactionRepository.sumExpensesByMonthAndYear(user.getUserId(), (LocalDate.now().minusMonths(1).getMonthValue()), LocalDate.now().minusMonths(1).getYear()));
            Savings savings = new Savings();
            savings.setUserId(user.getUserId());
            savings.setMonth(LocalDate.now().minusMonths(1).getMonthValue());
            savings.setYear(LocalDate.now().minusMonths(1).getYear());
            savings.setFinalAmount(lastMonthSavings);
            savingsRepository.save(savings);
        }
    }

}
