package com.company.kunuzdemo.controller;

import com.company.kunuzdemo.dtos.request.CommentRequestDTO;
import com.company.kunuzdemo.dtos.response.CommentResponseDTO;
import com.company.kunuzdemo.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CommentResponseDTO> create(
            @RequestBody @Valid CommentRequestDTO requestDTO
    ) {
        return ResponseEntity.ok(commentService.create(requestDTO));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{commentID}")
    public ResponseEntity<CommentResponseDTO> getByID(
            @PathVariable UUID commentID
    ) {
        return ResponseEntity.ok(commentService.getByID(commentID));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/get-by-article/{articleID}")
    public ResponseEntity<List<CommentResponseDTO>> getByArticleID(
            @PathVariable UUID articleID
    ) {
        return ResponseEntity.ok(commentService.getByArticleID(articleID));
    }


    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update-by-id/{commentID}")
    public  ResponseEntity<CommentResponseDTO> updateById(
            @PathVariable UUID commentID,
            @RequestParam String text
    ) {
        return ResponseEntity.ok(commentService.updateById(commentID, text));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @DeleteMapping("/delete/{commentID}")
    public ResponseEntity<String> delete(@PathVariable UUID commentID) {
        return ResponseEntity.ok(commentService.deleteById(commentID));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-selected/{commentIDs}")
    public ResponseEntity<String> deleteSelected(
            @PathVariable List<UUID> commentIDs) {
        return ResponseEntity.ok(commentService.deleteSelected(commentIDs));
    }
}
