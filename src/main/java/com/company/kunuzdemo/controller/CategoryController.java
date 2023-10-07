package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryDTO;
import com.company.kunuzdemo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/create")
    public CategoryDTO create(@Valid @RequestBody CategoryCreateDTO dto) {
        return categoryService.create(dto);
    }

    @GetMapping("/get-by-id/{categoryId}")
    public CategoryDTO getById(@PathVariable UUID categoryId) {
        return categoryService.getById(categoryId);
    }
}
