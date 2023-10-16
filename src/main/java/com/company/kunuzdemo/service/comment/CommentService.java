package com.company.kunuzdemo.service.comment;

import com.company.kunuzdemo.dtos.request.ArticleUpdateDTO;
import com.company.kunuzdemo.dtos.request.CommentRequestDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;
import com.company.kunuzdemo.dtos.response.CommentResponseDTO;
import com.company.kunuzdemo.service.user.UserService;

import java.util.List;
import java.util.UUID;

public interface CommentService {
    CommentResponseDTO create(CommentRequestDTO requestDTO);

    CommentResponseDTO getByID(UUID commentID);



}
