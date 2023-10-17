package com.company.kunuzdemo.controller;


import com.company.kunuzdemo.dtos.request.ArticleCreateDTO;
import com.company.kunuzdemo.dtos.request.ArticleUpdateDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;
import com.company.kunuzdemo.service.article.ArticleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final ArticleService articleService;


    @PostMapping
    public ResponseEntity<ArticleResponseDTO> create(
            @Valid @RequestBody ArticleCreateDTO createDTO
    ) {
        ArticleResponseDTO articleResponseDTO = articleService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleResponseDTO);
    }


    @GetMapping("/{articleID}")
    public ResponseEntity<ArticleResponseDTO> getById(
            @PathVariable UUID articleID
    ) {
        ArticleResponseDTO articleResponseDTO = articleService.getByID(articleID);
        return ResponseEntity.ok(articleResponseDTO);
    }


    @GetMapping("/get-by-language")
    public ResponseEntity<List<ArticleResponseDTO>> getByLanguage(
            @RequestParam String language,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        List<ArticleResponseDTO> byLanguage = articleService.getByLanguage(language, page, size);
        return ResponseEntity.ok(byLanguage);
    }


    @GetMapping("/recommended")
    public ResponseEntity<List<ArticleResponseDTO>> recommendedList(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        List<ArticleResponseDTO> recommendedList = articleService.recommendedList(page, size);
        return ResponseEntity.ok(recommendedList);
    }


    @GetMapping("/search-by-title")
    public ResponseEntity<List<ArticleResponseDTO>> searchByTitle(
            @RequestParam String title,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        List<ArticleResponseDTO> articleResponseDTOS = articleService.searchByTitle(title, page, size);
        return ResponseEntity.ok(articleResponseDTOS);
    }


    @PutMapping("/change-status/{articleID}")
    public ResponseEntity<String> changeStatus(
            @PathVariable UUID articleID,
            @RequestParam String status
    ) {
        String response = articleService.changeStatus(articleID, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ArticleResponseDTO>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(articleService.getAll(page, size));
    }

    //todo: change method name
    @GetMapping("/get-by-publisher/{createdById}")
    public ResponseEntity<List<ArticleResponseDTO>> getAllVisible(
            @PathVariable @NotNull UUID createdById,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(articleService.findByPublisher(createdById, page, size));
    }

    @GetMapping("/get-all-blocked")
    public ResponseEntity<List<ArticleResponseDTO>> getAllUnVisible(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(articleService.getAllBlocked(page, size));
    }

    @GetMapping("/get-by-region/{regionID}")
    public ResponseEntity<List<ArticleResponseDTO>> getByRegion(
            @PathVariable @NotNull UUID regionID,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(articleService.getByRegion(regionID, page, size));
    }

    @GetMapping("/get-latest-news")
    public ResponseEntity<List<ArticleResponseDTO>> getLatestNews(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size
    ) {
        return ResponseEntity.ok(articleService.getLatestNews(page, size));
    }

    @PutMapping("/{articleID}")
    public ResponseEntity<ArticleResponseDTO> updateById(
            @PathVariable @NotNull UUID articleID,
            @RequestBody @Valid ArticleUpdateDTO updateDTO
    ) {
        return ResponseEntity.ok(articleService.updateById(articleID, updateDTO));
    }

    @DeleteMapping("/{articleID}")
    public ResponseEntity<String> deleteById(
            @PathVariable @NotNull UUID articleID
    ) {
        return ResponseEntity.ok(articleService.deleteById(articleID));
    }

    @DeleteMapping("/delete-selected")
    public ResponseEntity<String> deleteSelected(
            @RequestParam @NotNull List<UUID> articleIDs
    ) {
        return ResponseEntity.ok(articleService.deleteSelected(articleIDs));
    }

}