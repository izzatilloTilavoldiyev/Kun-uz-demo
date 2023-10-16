package com.company.kunuzdemo.controller;


import com.company.kunuzdemo.dtos.request.ArticleCreateDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;
import com.company.kunuzdemo.service.article.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ArticleResponseDTO> create(
            @PathVariable UUID articleID
    ) {
        ArticleResponseDTO articleResponseDTO = articleService.getByID(articleID);
        return ResponseEntity.ok(articleResponseDTO);
    }

}