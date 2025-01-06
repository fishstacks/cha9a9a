package com.myprojects.madvisor.transactions.repository;

import com.myprojects.madvisor.transactions.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {

    @Query("SELECT SUM(s.finalAmount) " +
            "FROM Savings s " +
            "WHERE s.userId = :userId")
    BigDecimal sumFinalAmountByUserId(@Param("userId") Long userId);
    @Query("SELECT SUM(s.finalAmount) " +
            "FROM Savings s " +
            "WHERE s.userId = :userId AND s.month = :month AND s.year = :year")
    BigDecimal sumFinalAmountByUserIdAndMonthAndYear(@Param("userId") Long userId,
                                                     @Param("month") int month,
                                                     @Param("year") int year);


}
