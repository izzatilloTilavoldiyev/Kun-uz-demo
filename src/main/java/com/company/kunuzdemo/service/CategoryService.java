package com.company.kunuzdemo.service;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryDTO;

import java.util.UUID;

public interface CategoryService {
    CategoryDTO create(CategoryCreateDTO dto);
    CategoryDTO getById(UUID categoryId);
}
