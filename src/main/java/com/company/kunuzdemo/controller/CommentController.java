package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.CommentRequestDTO;
import com.company.kunuzdemo.dtos.response.CommentResponseDTO;
import com.company.kunuzdemo.dtos.response.RegionResponseDTO;
import com.company.kunuzdemo.service.comment.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<CommentResponseDTO> create(
            @RequestBody @Valid CommentRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(commentService.create(requestDTO));
    }

    @GetMapping("/{commentID}")
    public ResponseEntity<CommentResponseDTO> getById(
            @PathVariable @NotNull UUID commentID
    ) {
        return ResponseEntity.ok(commentService.getByID(commentID));
    }

    @GetMapping("/get-by-article/{articleID}")
    public ResponseEntity<List<CommentResponseDTO>> getByArticleID(
            @PathVariable @NotNull UUID articleID
    ) {
        return ResponseEntity.ok(commentService.getByArticleID(articleID));
    }

    @DeleteMapping("/delete/{commentID}")
    public ResponseEntity<String> delete(@PathVariable @NotNull UUID commentID) {
        return ResponseEntity.ok(commentService.deleteById(commentID));
    }

    @DeleteMapping("/delete-selected/{commentID}")
    public ResponseEntity<String> deleteSelected(
            @RequestParam @NotNull List<UUID> commentIDs) {
        return ResponseEntity.ok(commentService.deleteSelected(commentIDs));
    }
}
