package com.company.kunuzdemo.service.article;

import com.company.kunuzdemo.dtos.request.ArticleCreateDTO;
import com.company.kunuzdemo.dtos.request.ArticleUpdateDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;
import com.company.kunuzdemo.entity.*;
import com.company.kunuzdemo.enums.ArticleStatus;
import com.company.kunuzdemo.enums.Language;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.ArticleRepository;
import com.company.kunuzdemo.service.category.CategoryService;
import com.company.kunuzdemo.service.media.MediaService;
import com.company.kunuzdemo.service.region.RegionService;
import com.company.kunuzdemo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.channels.IllegalBlockingModeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final RegionService regionService;
    private final CategoryService categoryService;
    private final MediaService mediaService;

    @Override
    public Article getArticle(UUID articleID) {
        return getArticleByID(articleID);
    }


    @Override
    public ArticleResponseDTO create(ArticleCreateDTO articleCreateDTO) {
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


    @Override
    public List<ArticleResponseDTO> getByLanguage(String language, Integer page, Integer size) {
        List<Article> articles = articleRepository.findByLanguage(
                Language.valueOf(language), PageRequest.of(page, size)).getContent();
        return modelMapper.map(articles, new TypeToken<List<ArticleResponseDTO>>() {
        }.getType());
    }


    @Override
    public List<ArticleResponseDTO> recommendedList(Integer page, Integer size) {
        List<Article> recommendedList = articleRepository
                .findRecommendedArticles(PageRequest.of(page, size)).getContent();
        return modelMapper.map(recommendedList, new TypeToken<List<ArticleResponseDTO>>() {
        }.getType());
    }


    @Override
    public List<ArticleResponseDTO> searchByTitle(String title, Integer page, Integer size) {
        List<Article> articles = articleRepository.searchByTitle(title, PageRequest.of(page, size)).getContent();
        return modelMapper.map(articles, new TypeToken<List<ArticleResponseDTO>>() {
        }.getType());
    }


    @Override
    public List<ArticleResponseDTO> getAll(Integer page, Integer size) {
        return modelMapper.map(articleRepository.getAll(PageRequest.of(page, size)).getContent(),
                new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }


    @Override
    public List<ArticleResponseDTO> getByPublisher(Integer page, Integer size) {
        return modelMapper.map(articleRepository.findByPublished(PageRequest.of(page, size)).getContent(),
                new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }


    @Override
    public List<ArticleResponseDTO> getByRegion(UUID regionID, Integer page, Integer size) {
        return modelMapper.map(articleRepository.findArticleByRegion(regionID, PageRequest.of(page, size)).getContent(),
                new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }


    @Override
    public List<ArticleResponseDTO> getAllBlocked(Integer page, Integer size) {
        return modelMapper.map(articleRepository.findArticleByStatusBlocked(PageRequest.of(page, size)).getContent(),
                new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }


    @Override
    public List<ArticleResponseDTO> getLatestNews(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        return modelMapper.map(articleRepository.findLatestNews(PageRequest.of(page, size, sort)).getContent(),
                new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }


    @Override
    public ArticleResponseDTO updateById(UUID articleID, ArticleUpdateDTO updateDTO) {
        Article article = getArticle(articleID);
        Media media = mediaService.getMediaById(updateDTO.getMediaID());
        System.out.println();
        if(!media.isDeleted()) {
            try {
                article.setLanguage(Language.valueOf(updateDTO.getLanguage()));
                article.setTitle(updateDTO.getTitle());
                article.setDescription(updateDTO.getDescription());
                article.setCategory(categoryService.getCategory(updateDTO.getCategoryID()));
                article.setRegion(regionService.getRegion(updateDTO.getReginID()));
                article.setMedia(media);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Enum type not valid: " + updateDTO.getLanguage());
            }
        }
        return modelMapper.map(articleRepository.save(article), ArticleResponseDTO.class);
    }


    @Override
    public String deleteById(UUID articleID) {
        Article article = getArticle(articleID);
        article.setDeleted(true);
        articleRepository.save(article);
        return "Successfully deleted!";
    }


    @Override
    public String deleteSelected(List<UUID> articleIDs) {
        for (UUID articleID : articleIDs) {
            deleteById(articleID);
        }
        return "Successfully deleted!";
    }


    @Override
    public String changeStatus(UUID articleID, String status) {
        Article article = getArticleByID(articleID);
        article.setStatus(ArticleStatus.valueOf(status));
        if (Objects.equals(article.getStatus(), ArticleStatus.PUBLISHED))
            article.setPublishedDate(LocalDateTime.now());
        articleRepository.save(article);
        return "Status successfully changed";
    }


    private Article getArticleByID(UUID articleID) {
        return articleRepository.findArticleByID(articleID).orElseThrow(
                () -> new DataNotFoundException("Article not found for ID: " + articleID)
        );
    }

}