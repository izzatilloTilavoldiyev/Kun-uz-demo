package com.company.kunuzdemo.service.like;


import com.company.kunuzdemo.dtos.request.LIkeRequestDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;
import com.company.kunuzdemo.dtos.response.LikeResponseDTO;
import com.company.kunuzdemo.entity.Article;
import com.company.kunuzdemo.entity.Like;
import com.company.kunuzdemo.entity.User;
import com.company.kunuzdemo.enums.ArticleStatus;
import com.company.kunuzdemo.enums.LikeStatus;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.LikeRepository;
import com.company.kunuzdemo.service.article.ArticleService;
import com.company.kunuzdemo.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ArticleService articleService;


    @Override
    public LikeResponseDTO create(LIkeRequestDTO dto) {
        try {
            User user = userService.getUserByID(dto.getUserID());
            Article article = articleService.getArticle(dto.getArticleID());
            Like like = Like.builder()
                    .status(LikeStatus.valueOf(dto.getStatus()))
                    .user(user)
                    .article(article)
                    .build();
            Like savedLike = likeRepository.save(like);
            return modelMapper.map(savedLike, LikeResponseDTO.class);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum type not valid: " + dto.getStatus());
        }
    }


    @Override
    public LikeResponseDTO getByID(UUID likeID) {
        return modelMapper.map(getLikeByID(likeID), LikeResponseDTO.class);
    }


    @Override
    public List<LikeResponseDTO> getLikeByArticleID(UUID articleID, Integer page, Integer size) {
        Article article = articleService.getArticle(articleID);
        List<Like> likesByArticleID = likeRepository
                .findLikeByArticleID(article.getId(), PageRequest.of(page, size)).getContent();
        return modelMapper.map(likesByArticleID, new TypeToken<List<LikeResponseDTO>>() {}.getType());
    }


    @Override
    public List<LikeResponseDTO> getDislikeByArticleID(UUID articleID, Integer page, Integer size) {
        Article article = articleService.getArticle(articleID);
        List<Like> likesByArticleID = likeRepository
                .findDislikeByArticleID(article.getId(), PageRequest.of(page, size)).getContent();
        return modelMapper.map(likesByArticleID, new TypeToken<List<LikeResponseDTO>>() {}.getType());
    }


    @Transactional
    @Modifying
    @Override
    public void deleteByID(UUID likeID) {
        if (!likeRepository.existsById(likeID))
            throw new DataNotFoundException("Like not found with ID: " + likeID);
        likeRepository.deleteById(likeID);
    }


    private Like getLikeByID(UUID likeID) {
        return likeRepository.findById(likeID).orElseThrow(
                () -> new DataNotFoundException("Like not found with ID: " + likeID)
        );
    }
}