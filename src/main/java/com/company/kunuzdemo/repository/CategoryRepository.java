package com.company.kunuzdemo.repository;

import com.company.kunuzdemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query(value = "from category c where c.id = :categoryID and c.visible = true")
    Optional<Category> findCategoryById(@Param("categoryID") UUID categoryID);


    @Query(value = "from category c where c.visible = true ")
    List<Category> findAllVisible();

    @Query(value = "from category c where c.visible = false ")
    List<Category> findAllUnVisible();
}
