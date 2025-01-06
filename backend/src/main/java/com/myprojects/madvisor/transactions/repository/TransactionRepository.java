package com.myprojects.madvisor.transactions.repository;

import com.myprojects.madvisor.transactions.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByUserId(Long userId, Pageable pageable);
    List<Transaction> findAllByIsFixedTrue();

    Optional<Transaction> findByIdAndUserId(Long transactionId, Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Transaction t WHERE t.id = :transactionId AND t.user.id = :userId")
    void deleteByIdAndUserId(@Param("transactionId") Long transactionId, @Param("userId") Long userId);

    @Query("SELECT t FROM Transaction t WHERE t.type = 'Income' AND FUNCTION('MONTH', t.date) = :month AND FUNCTION('YEAR', t.date) = :year AND t.user.id = :userId")
    List<Transaction> findIncomesByMonthAndYear(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);

    @Query("SELECT t FROM Transaction t WHERE t.type = 'Expense' AND FUNCTION('MONTH', t.date) = :month AND FUNCTION('YEAR', t.date) = :year AND t.user.id = :userId")
    List<Transaction> findExpensesByMonthAndYear(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = 'Income' AND FUNCTION('MONTH', t.date) = :month AND FUNCTION('YEAR', t.date) = :year AND t.user.id = :userId")
    BigDecimal sumIncomesByMonthAndYear(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = 'Expense' AND FUNCTION('MONTH', t.date) = :month AND FUNCTION('YEAR', t.date) = :year AND t.user.id = :userId")
    BigDecimal sumExpensesByMonthAndYear(@Param("userId") Long userId, @Param("month") int month, @Param("year") int year);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = 'Income' AND t.user.id = :userId")
    BigDecimal sumAllIncomes(@Param("userId") Long userId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = 'Expense' AND t.user.id = :userId")
    BigDecimal sumAllExpenses(@Param("userId") Long userId);

    @Query("SELECT t.category, SUM(t.amount) FROM Transaction t WHERE t.type = 'Income' AND t.user.id = :userId GROUP BY t.category")
    List<Object[]> sumAllIncomesByCategory(@Param("userId") Long userId);

    @Query("SELECT new com.myprojects.madvisor.transactions.dto.CategoryDTO(c.categoryId, c.name, c.icon), SUM(t.amount) " +
            "FROM Transaction t JOIN t.category c " +
            "WHERE t.type = 'Expense' AND t.user.id = :userId " +
            "GROUP BY c.categoryId, c.name, c.icon")
    List<Object[]> sumAllExpensesByCategory(@Param("userId") Long userId);




}
