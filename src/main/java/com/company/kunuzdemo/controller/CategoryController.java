package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.company.kunuzdemo.dtos.request.CategoryUpdateDTO;
import com.company.kunuzdemo.service.category.CategoryServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {

        CategoryResponseDTO categoryResponseDTO = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDTO);
    }

    @GetMapping("/get-by-id/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getById(@Valid @PathVariable @NotNull UUID categoryId) {

        return ResponseEntity.ok(categoryService.getById(categoryId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {

        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/get-all-visible")
    public ResponseEntity<List<CategoryResponseDTO>> getAllVisible() {

        return ResponseEntity.ok(categoryService.getAllVisible());
    }

    @GetMapping("/get-all-un-visible")
    public ResponseEntity<List<CategoryResponseDTO>> getAllUnVisible() {

        return ResponseEntity.ok(categoryService.getAllUnVisible());
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateById(
            @NotNull
            @PathVariable
            @Valid UUID categoryId,
            @RequestBody CategoryUpdateDTO dto
    ) {

        return ResponseEntity.ok(categoryService.updateById(categoryId, dto));
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> delete(@Valid @PathVariable @NotNull UUID categoryId) {

        categoryService.deleteById(categoryId);
        return ResponseEntity.ok("Successfully deleted!");
    }

    @DeleteMapping("/all-selected")
    public ResponseEntity<String> deleteSelectedCategories(
            @RequestBody List<UUID> categoryIDs
    ) {
        categoryService.deleteSelectedCategories(categoryIDs);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
