package com.myprojects.madvisor.transactions.util;

import com.myprojects.madvisor.transactions.model.Transaction;
import com.myprojects.madvisor.transactions.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecurrenceScheduler {
    @Autowired
    private TransactionRepository transactionRepository;



    @Scheduled(cron = "0 0 0 * * ?")
    public void generateRecurringTransactions() {
        List<Transaction> recurringTransactions = transactionRepository.findAllByIsFixedTrue();
        LocalDate today = LocalDate.now();

        for (Transaction transaction : recurringTransactions) {
            LocalDate nextDate = calculateNextTransactionDate(transaction);
            if (nextDate != null && nextDate.equals(today)) {
                Transaction newTransaction = new Transaction();
                newTransaction.setUser(transaction.getUser());
                newTransaction.setCategory(transaction.getCategory());
                newTransaction.setAmount(transaction.getAmount());
                newTransaction.setType(transaction.getType());
                newTransaction.setDate(nextDate);
                newTransaction.setDescription(transaction.getDescription());
                newTransaction.setIsFixed(transaction.getIsFixed());
                newTransaction.setFrequency(transaction.getFrequency());

                transactionRepository.save(newTransaction);
            }
        }
    }

    private LocalDate calculateNextTransactionDate(Transaction transaction) {
        switch (transaction.getFrequency()) {
            case "monthly":
                return transaction.getDate().plusMonths(1);
            case "yearly":
                return transaction.getDate().plusYears(1);
            default:
                return null;
        }
    }
}
