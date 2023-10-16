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
}
