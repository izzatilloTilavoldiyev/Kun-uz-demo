package com.company.kunuzdemo.service;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryDTO;
import com.company.kunuzdemo.entity.Category;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryDTO create(CategoryCreateDTO dto) {

        Category category = modelMapper.map(dto, Category.class);
        categoryRepository.save(category);
        return modelMapper.map(category, CategoryDTO.class);

    }

    public CategoryDTO getById(UUID categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new DataNotFoundException("category not found"));
        return modelMapper.map(category, CategoryDTO.class);
    }
}
