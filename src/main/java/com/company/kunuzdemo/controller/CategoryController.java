package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.CategoryCreateDTO;
import com.company.kunuzdemo.dtos.response.CategoryResponseDTO;
import com.company.kunuzdemo.dtos.request.CategoryUpdateDTO;
import com.company.kunuzdemo.service.category.CategoryServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        CategoryResponseDTO categoryResponseDTO = categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDTO);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/get-by-id/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> getById(@PathVariable UUID categoryId) {
        return ResponseEntity.ok(categoryService.getById(categoryId));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/get-all-visible")
    public ResponseEntity<List<CategoryResponseDTO>> getAllVisible() {
        return ResponseEntity.ok(categoryService.getAllVisible());
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/get-all-un-visible")
    public ResponseEntity<List<CategoryResponseDTO>> getAllUnVisible() {
        return ResponseEntity.ok(categoryService.getAllUnVisible());
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> updateById(
            @PathVariable UUID categoryId,
            @Valid @RequestBody CategoryUpdateDTO dto
    ) {
        return ResponseEntity.ok(categoryService.updateById(categoryId, dto));
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> delete(@PathVariable UUID categoryId) {
        categoryService.deleteById(categoryId);
        return ResponseEntity.ok("Successfully deleted!");
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/all-selected")
    public ResponseEntity<String> deleteSelectedCategories(
            @RequestParam List<UUID> categoryIDs
    ) {
        categoryService.deleteSelectedCategories(categoryIDs);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
