package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryDTO;
import com.company.kunuzdemo.dtos.response.CategoryUpdateDTO;
import com.company.kunuzdemo.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<CategoryDTO> getById(@Valid @PathVariable UUID categoryId) {

        return ResponseEntity.ok(categoryService.getById(categoryId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryDTO>> getAll() {

        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/get-all-visible")
    public ResponseEntity<List<CategoryDTO>> getAllVisible() {

        return ResponseEntity.ok(categoryService.getAllVisible());
    }

    @GetMapping("/get-all-un-visible")
    public ResponseEntity<List<CategoryDTO>> getAllUnVisible() {

        return ResponseEntity.ok(categoryService.getAllUnVisible());
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDTO> updateById(
            @Valid
            @PathVariable UUID categoryId,
            @RequestBody CategoryUpdateDTO dto
    ) {

        return ResponseEntity.ok(categoryService.updateById(categoryId, dto));
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> delete(@Valid @PathVariable UUID categoryId) {

        categoryService.deleteById(categoryId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/all-selected")
    public ResponseEntity<String> deleteSelectedCategories(
            @RequestBody List<UUID> categoryIDs
    ) {
        categoryService.deleteSelectedCategories(categoryIDs);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
