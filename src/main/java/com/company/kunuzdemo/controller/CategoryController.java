package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryDTO;
import com.company.kunuzdemo.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        CategoryDTO categoryDTO = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDTO);
    }

    @GetMapping("/get-by-id/{categoryId}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(categoryService.getById(categoryId));
    }
}
