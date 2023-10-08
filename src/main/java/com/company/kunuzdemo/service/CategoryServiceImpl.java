package com.company.kunuzdemo.service;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryDTO;
import com.company.kunuzdemo.entity.Category;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO create(CategoryCreateDTO dto) {

        Category category = modelMapper.map(dto, Category.class);
        Category saveCategory = categoryRepository.save(category);
        return modelMapper.map(saveCategory, CategoryDTO.class);

    }

    @Override
    public CategoryDTO getById(UUID categoryId) {

        Category category = categoryRepository.findCategoryById(categoryId).orElseThrow(
                () -> new DataNotFoundException("category not found"));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAll() {

        List<Category> categoryList = categoryRepository.findAll();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryDTO>>() {}.getType());
    }

    @Override
    public List<CategoryDTO> getAllVisible() {

        List<Category> categoryList = categoryRepository.findAllVisible();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryDTO>>() {}.getType());
    }

    @Override
    public List<CategoryDTO> getAllUnVisible() {

        List<Category> categoryList = categoryRepository.findAllUnVisible();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryDTO>>() {}.getType());
    }

    @Override
    public CategoryDTO updateById(UUID categoryId, CategoryDTO dto) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new DataNotFoundException("category not found"));
        category.setNameUZ(dto.getNameUZ());
        category.setNameRU(dto.getNameRU());
        category.setNameEN(dto.getNameEN());
        Category categoryUpdate = categoryRepository.save(category);
        return modelMapper.map(categoryUpdate, CategoryDTO.class);
    }

    @Override
    public void deleteById(UUID categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new DataNotFoundException("category not found"));
        categoryRepository.deleteById(categoryId);
    }
}
