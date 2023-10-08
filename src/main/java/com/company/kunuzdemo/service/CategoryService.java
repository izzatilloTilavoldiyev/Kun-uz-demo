package com.company.kunuzdemo.service;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryDTO;
import com.company.kunuzdemo.dtos.response.CategoryUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO create(CategoryCreateDTO dto);
    CategoryDTO getById(UUID categoryId);

    CategoryDTO updateById(UUID categoryId, CategoryUpdateDTO dto);

    List<CategoryDTO> getAll();

    List<CategoryDTO> getAllVisible();
    List<CategoryDTO> getAllUnVisible();

    void deleteById(UUID categoryId);
}
