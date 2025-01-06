package com.myprojects.madvisor.transactions.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest {
    @NotNull
    private BigDecimal amount;

    @NotNull
    @Pattern(regexp = "Income|Expense")
    private String type;

    @NotNull
    @PastOrPresent
    private LocalDate date;

    @Size(max = 255)
    private String description;

    @NotNull
    private Long categoryId;

    private Boolean isFixed;

    @Pattern(regexp = "Weekly|Monthly|Yearly")
    private String frequency;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Boolean isFixed) {
        this.isFixed = isFixed;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
