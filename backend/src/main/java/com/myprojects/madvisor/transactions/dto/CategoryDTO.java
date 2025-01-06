package com.myprojects.madvisor.transactions.dto;


import jakarta.validation.constraints.NotNull;

public class CategoryDTO {

    @NotNull
    private Long categoryId;

    @NotNull
    private String name;
    @NotNull
    private String icon;

    public CategoryDTO(Long categoryId, String name, String icon) {
        this.categoryId = categoryId;
        this.name = name;
        this.icon = icon;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
