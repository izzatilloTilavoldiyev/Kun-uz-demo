package com.company.kunuzdemo.controller;


import com.company.kunuzdemo.dtos.request.LIkeRequestDTO;
import com.company.kunuzdemo.dtos.response.LikeResponseDTO;
import com.company.kunuzdemo.service.like.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {

    private final LikeService likeService;


    @PostMapping
    public ResponseEntity<LikeResponseDTO> create(
            @Valid @RequestBody LIkeRequestDTO lIkeRequestDTO
    ) {
        LikeResponseDTO likeResponseDTO = likeService.create(lIkeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(likeResponseDTO);
    }


    @GetMapping("/{likeID}")
    public ResponseEntity<LikeResponseDTO> getByID(
            @PathVariable UUID likeID
    ) {
        return ResponseEntity.ok(likeService.getByID(likeID));
    }


    @GetMapping("/get-likes-by-article")
    public ResponseEntity<List<LikeResponseDTO>> getLikeByArticleID(
            @RequestParam UUID articleID,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(likeService.getLikeByArticleID(articleID, page, size));
    }


    @GetMapping("/get-dislikes-by-article")
    public ResponseEntity<List<LikeResponseDTO>> getDislikeByArticleID(
            @RequestParam UUID articleID,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(likeService.getDislikeByArticleID(articleID, page, size));
    }


    @DeleteMapping("/{likeID}")
    public ResponseEntity<String> delete(
            @PathVariable UUID likeID
    ) {
        likeService.deleteByID(likeID);
        return ResponseEntity.ok("Successfully deleted!");
    }
}