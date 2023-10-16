package com.company.kunuzdemo.service.article;


import com.company.kunuzdemo.dtos.request.ArticleCreateDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;
import com.company.kunuzdemo.entity.Article;
import com.company.kunuzdemo.entity.Category;
import com.company.kunuzdemo.entity.Region;
import com.company.kunuzdemo.entity.User;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.exception.DuplicateValueException;
import com.company.kunuzdemo.repository.ArticleRepository;
import com.company.kunuzdemo.service.category.CategoryService;
import com.company.kunuzdemo.service.media.MediaService;
import com.company.kunuzdemo.service.region.RegionService;
import com.company.kunuzdemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final MediaService mediaService;


    @Override
    public ArticleResponseDTO create(ArticleCreateDTO articleCreateDTO) {
        checkArticleUniqueByTitle(articleCreateDTO.getTitle());
        User user = userService.getUserByID(articleCreateDTO.getUserID());
        Region region = regionService.getRegion(articleCreateDTO.getRegionID());
        Category category = categoryService.getCategory(articleCreateDTO.getCategoryID());
        Article article = modelMapper.map(articleCreateDTO, Article.class);
        article.setCreatedBy(user);
        article.setRegion(region);
        article.setCategory(category);
        if (articleCreateDTO.getMediaID() != null)
            article.setMedia(mediaService.getMediaById(articleCreateDTO.getMediaID()));
        Article savedArticle = articleRepository.save(article);
        return modelMapper.map(savedArticle, ArticleResponseDTO.class);
    }


    @Override
    public ArticleResponseDTO getByID(UUID articleID) {
        Article article = getArticleByID(articleID);
        article.setViewCount(article.getViewCount() + 1);
        Article savedArticle = articleRepository.save(article);
        return modelMapper.map(savedArticle, ArticleResponseDTO.class);
    }


    private Article getArticleByID(UUID articleID) {
        return articleRepository.findArticleByID(articleID).orElseThrow(
                () -> new DataNotFoundException("Article not found for ID: " + articleID)
        );
    }

    private void checkArticleUniqueByTitle(String title) {
        if (articleRepository.existsByTitle(title))
            throw new DuplicateValueException("Article already exists with Title: " + title);
    }
}