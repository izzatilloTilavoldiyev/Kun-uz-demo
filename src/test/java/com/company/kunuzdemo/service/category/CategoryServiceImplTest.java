package com.company.kunuzdemo.service.category;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.request.CategoryUpdateDTO;
import com.company.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.company.kunuzdemo.entity.Category;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ModelMapper modelMapper;
    private CategoryService categoryService;
    private CategoryCreateDTO dto;
    private Category category;
    private CategoryResponseDTO responseDTO;
    private List<Category> categoryList;
    private CategoryResponseDTO categoryResponseDTO;
    private CategoryUpdateDTO updateDTO;
    private UUID categoryId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        categoryService = new CategoryServiceImpl(categoryRepository, modelMapper);
        category = new Category();
        responseDTO = new CategoryResponseDTO();
        categoryList = new ArrayList<>();
        categoryId = UUID.randomUUID();
    }

    @Test
    void create() {
        dto = CategoryCreateDTO.builder()
                .nameEN("EN")
                .nameRU("RU")
                .nameUZ("UZ")
                .build();
        when(modelMapper.map(dto, Category.class)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryResponseDTO.class)).thenReturn(responseDTO);
        CategoryResponseDTO rez = categoryService.create(dto);
        assertEquals(rez, responseDTO);
    }

    @Test
    void getAll() {
        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(modelMapper.map(categoryList, new TypeToken<List<CategoryResponseDTO>>() {}.getType()))
                .thenReturn(categoryResponseDTO);
        List<CategoryResponseDTO> result = categoryService.getAll();
        assertEquals(result, categoryResponseDTO);
    }

    @Test
    void getById() {
        when(modelMapper.map(categoryRepository.findCategoryById(categoryId), CategoryResponseDTO.class))
                .thenReturn(categoryResponseDTO);
        when(categoryRepository.findCategoryById(categoryId)).thenReturn(Optional.of(category));
        CategoryResponseDTO rez = categoryService.getById(categoryId);
        assertEquals(rez, categoryResponseDTO);
    }

    @Test
    void getByIdFail() {
        when(categoryRepository.findCategoryById(categoryId)).thenReturn(Optional.of(category));
        assertThrows(DataNotFoundException.class, () -> categoryService.getCategory(UUID.randomUUID()));

//        verify(categoryRepository.findCategoryById(any(UUID.class)), times(1))
        verify(categoryRepository).findCategoryById(any(UUID.class));
    }

    @Test
    void getAllVisible() {
        when(categoryRepository.findAllVisible()).thenReturn(categoryList);
        when(modelMapper.map(categoryList, new TypeToken<List<CategoryResponseDTO>>() {
        }.getType()))
                .thenReturn(categoryResponseDTO);
        List<CategoryResponseDTO> result = categoryService.getAllVisible();

        assertEquals(result, categoryResponseDTO);
    }

    @Test
    void getAllUnVisible() {
        when(categoryRepository.findAllUnVisible()).thenReturn(categoryList);
        when(modelMapper.map(categoryList, new TypeToken<List<CategoryResponseDTO>>() {}.getType()))
                .thenReturn(categoryResponseDTO);
        List<CategoryResponseDTO> result = categoryService.getAllVisible();

        assertEquals(result, categoryResponseDTO);
    }

    @Test
    void updateById() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        doNothing().when(modelMapper).map(dto, category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(modelMapper.map(category, CategoryResponseDTO.class)).thenReturn(categoryResponseDTO);
        CategoryResponseDTO rez = categoryService.updateById(categoryId, updateDTO);

        assertEquals(rez, categoryResponseDTO);
    }

    @Test
    void updateFileById() {
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        assertThrows(DataNotFoundException.class, () -> categoryService.updateById(UUID.randomUUID(), updateDTO));
    }

    @Test
    void deleteById() {
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(categoryId);
        assertThrows(DataNotFoundException.class, () -> categoryService.deleteById(categoryId));
    }

    @Test
    void deleteFileById() {
        when(categoryRepository.existsById(categoryId)).thenReturn(false);
        assertThrows(DataNotFoundException.class, () -> categoryService.deleteById(categoryId));
    }

    @Test
    void deleteSelectedCategories() {
    }


    @Test
    void getCategoryById() {
        when(categoryRepository.findCategoryById(categoryId)).thenReturn(Optional.of(category));
        Category rez = categoryService.getCategory(categoryId);
        assertEquals(rez, category);
    }
}