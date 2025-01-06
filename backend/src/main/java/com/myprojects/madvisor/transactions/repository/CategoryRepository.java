package com.myprojects.madvisor.transactions.repository;

import com.myprojects.madvisor.transactions.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryIdAndUserId(Long categoryId, Long userId);
    Category findByCategoryId(Long categoryId);

    List<Category> findByUserId(Long uerId);

    /*@Transactional
    @Modifying
    @Query("DELETE FROM Category c WHERE c.categoryId = :categoryId AND c.user.id = :userId")
    void deleteByCategoryIdAndUserId(@Param("categoryId") Long categoryId, @Param("userId") Long userId);*/

    void deleteByCategoryId(Long categoryId);

}
