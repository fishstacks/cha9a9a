package com.myprojects.madvisor.transactions.controller;

import com.myprojects.madvisor.security.user.CustomUserDetails;
import com.myprojects.madvisor.transactions.dto.CategoryDTO;
import com.myprojects.madvisor.transactions.model.Category;
import com.myprojects.madvisor.transactions.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

        public CategoryController(CategoryService categoryService) {
            this.categoryService = categoryService;
        }

        @GetMapping
        public List<CategoryDTO> getCategoriesForUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
            Long userId = userDetails.getUserId();
            return categoryService.getCategoriesForUser(userId);
        }


        @PostMapping
        public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO request,
                                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
            Long userId = userDetails.getUserId();
            Category category = categoryService.createCategory(request, userId);
            return ResponseEntity.ok(category);

        }

        @PutMapping
        public ResponseEntity<Category> updateCategory(
                @RequestParam("categoryId") Long categoryId,
                @RequestBody @Valid CategoryDTO categoryDTO,
                @AuthenticationPrincipal CustomUserDetails userDetails) {
            Long userId = userDetails.getUserId();
            Category updatedCategory = categoryService.updateCategory(userId, categoryId, categoryDTO);
            return ResponseEntity.ok(updatedCategory);
        }


        @DeleteMapping
        public ResponseEntity<String> deleteProduct(@RequestParam("categoryId") Long categoryId,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails){
            Long userId = userDetails.getUserId();
            boolean deleted = categoryService.deleteCategory(categoryId);
            if (deleted)
                return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
}
