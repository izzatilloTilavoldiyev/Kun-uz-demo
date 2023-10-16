package com.company.kunuzdemo.service.comment;

import com.company.kunuzdemo.dtos.request.CommentRequestDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;
import com.company.kunuzdemo.dtos.response.CommentResponseDTO;
import com.company.kunuzdemo.dtos.response.UserResponseDTO;
import com.company.kunuzdemo.entity.Article;
import com.company.kunuzdemo.entity.Comment;
import com.company.kunuzdemo.entity.User;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.CommentRepository;
import com.company.kunuzdemo.service.article.ArticleService;
import com.company.kunuzdemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final UserService userService;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    @Override
    public CommentResponseDTO create(CommentRequestDTO requestDTO) {
        User user = userService.getUserByID(requestDTO.getUserID());
        Article article = articleService.getArticleById(requestDTO.getArticleID());
        Comment comment = modelMapper.map(requestDTO, Comment.class);
        comment.setUser(user);
        comment.setArticle(article);

        return modelMapper.map(commentRepository.save(comment), CommentResponseDTO.class);
    }

    @Override
    public CommentResponseDTO getByID(UUID commentID) {
        return modelMapper.map(getByCommentId(commentID), CommentResponseDTO.class);
    }
    private Comment getByCommentId(UUID commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new DataNotFoundException("comment not found with ID :" + commentId));
    }
}
