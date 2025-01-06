package com.myprojects.madvisor.transactions.service;

import com.myprojects.madvisor.security.user.User;
import com.myprojects.madvisor.security.user.UserRepository;
import com.myprojects.madvisor.transactions.dto.CategoryDTO;
import com.myprojects.madvisor.transactions.model.Category;
import com.myprojects.madvisor.transactions.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public CategoryService(UserRepository userRepository,
                              CategoryRepository categoryRepository) {

        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CategoryDTO request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Category category = new Category();
        category.setUser(user);
        category.setName(request.getName());
        category.setIcon(request.getIcon());

        categoryRepository.save(category);

        return category;
    }

    public List<CategoryDTO> getCategoriesForUser(Long userId) {

        List<Category> userCategories = categoryRepository.findByUserId(userId);

        return userCategories.stream()
                .map(category -> new CategoryDTO(
                        category.getCategoryId(),
                        category.getName(),
                        category.getIcon()
                ))
                .collect(Collectors.toList());
    }



    public Category updateCategory(Long userId, Long categoryId, CategoryDTO request) {
        Category category = categoryRepository.findByCategoryIdAndUserId(categoryId,userId);
            User user = userRepository.
                    findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            category.setName(request.getName());
            category.setIcon(request.getIcon());



        categoryRepository.save(category);

        return category;
    }

    public boolean deleteCategory(Long categoryId) {

        try {
            categoryRepository.deleteByCategoryId(categoryId);
            return true;

        } catch (Exception e) {
            return false;
        }

    }
}
