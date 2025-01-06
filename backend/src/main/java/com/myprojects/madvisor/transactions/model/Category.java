package com.myprojects.madvisor.transactions.model;

import com.myprojects.madvisor.security.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    private String icon;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Transaction> transactions;



    public Category() {
    }

    public Category(String name, String icon, User user) {
        this.user = user;
        this.name = name;
        this.icon = icon;
    }

    public Category(String name, String icon) {
        this.name = name;
        this.icon = icon;

    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
