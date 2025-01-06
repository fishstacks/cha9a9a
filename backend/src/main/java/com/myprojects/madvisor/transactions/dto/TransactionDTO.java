package com.myprojects.madvisor.transactions.dto;

import com.myprojects.madvisor.security.user.User;
import com.myprojects.madvisor.transactions.model.Category;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDTO {
    private Long id;

    private Category category;

    private BigDecimal amount;

    private String type;

    private LocalDate date;

    private String description;

    private Boolean isFixed = false;

    public TransactionDTO(Long id, Category category, BigDecimal amount, String type, LocalDate date, String description, Boolean isFixed) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.description = description;
        this.isFixed = isFixed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFixed() {
        return isFixed;
    }

    public void setFixed(Boolean fixed) {
        isFixed = fixed;
    }
}
