package com.company.kunuzdemo.service.category;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.company.kunuzdemo.dtos.request.CategoryUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryResponseDTO create(CategoryCreateDTO dto);

    CategoryResponseDTO getById(UUID categoryId);

    CategoryResponseDTO updateById(UUID categoryId, CategoryUpdateDTO dto);

    List<CategoryResponseDTO> getAll();

    List<CategoryResponseDTO> getAllVisible();

    List<CategoryResponseDTO> getAllUnVisible();

    void deleteById(UUID categoryId);

    void deleteSelectedCategories(List<UUID> categoryIDs);
}
