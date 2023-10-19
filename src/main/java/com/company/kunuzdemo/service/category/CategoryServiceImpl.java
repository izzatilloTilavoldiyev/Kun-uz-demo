package com.company.kunuzdemo.service.category;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.company.kunuzdemo.dtos.request.CategoryUpdateDTO;
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
    public CategoryResponseDTO create(CategoryCreateDTO dto) {

        Category category = modelMapper.map(dto, Category.class);
        Category saveCategory = categoryRepository.save(category);
        return modelMapper.map(saveCategory, CategoryResponseDTO.class);

    }

    @Override
    public Category getCategory(UUID categoryID) {
        return getCategoryById(categoryID);
    }

    @Override
    public List<CategoryResponseDTO> getAll() {

        List<Category> categoryList = categoryRepository.findAll();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryResponseDTO>>() {}.getType());
    }

    @Override
    public CategoryResponseDTO getById(UUID categoryId) {
        return modelMapper.map(getCategoryById(categoryId), CategoryResponseDTO.class);
    }

    @Override
    public List<CategoryResponseDTO> getAllVisible() {

        List<Category> categoryList = categoryRepository.findAllVisible();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryResponseDTO>>() {}.getType());
    }

    @Override
    public List<CategoryResponseDTO> getAllUnVisible() {

        List<Category> categoryList = categoryRepository.findAllUnVisible();
        return modelMapper.map(categoryList, new TypeToken<List<CategoryResponseDTO>>() {}.getType());
    }

    @Override
    public CategoryResponseDTO updateById(UUID categoryId, CategoryUpdateDTO dto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new DataNotFoundException("category not found with ID :" + categoryId));
        modelMapper.map(dto, category);
        Category update = categoryRepository.save(category);
        return modelMapper.map(update, CategoryResponseDTO.class);
    }

    @Override
    public void deleteById(UUID categoryId) {
        if(categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        }
        throw new DataNotFoundException("category not found with ID :" + categoryId);
    }

    @Override
    public void deleteSelectedCategories(List<UUID> categoryIDs) {
        for (UUID categoryID : categoryIDs) {
            deleteById(categoryID);
        }
    }

    private Category getCategoryById(UUID categoryId) {
        return categoryRepository.findCategoryById(categoryId).orElseThrow(
                () -> new DataNotFoundException("category not found with ID :" + categoryId));
    }

}
