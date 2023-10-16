package com.company.kunuzdemo.service.article;

import com.company.kunuzdemo.dtos.request.ArticleCreateDTO;
import com.company.kunuzdemo.dtos.request.ArticleUpdateDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;
import com.company.kunuzdemo.entity.Article;
import com.company.kunuzdemo.entity.Category;
import com.company.kunuzdemo.entity.Region;
import com.company.kunuzdemo.entity.User;
import com.company.kunuzdemo.enums.ArticleStatus;
import com.company.kunuzdemo.enums.Language;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.exception.DuplicateValueException;
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

import java.util.List;
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
    public Article getArticle(UUID articleID) {
        return getArticleByID(articleID);
    }

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

    @Override
    public List<ArticleResponseDTO> getByLanguage(String language, Integer page, Integer size) {
        List<Article> articles = articleRepository.findByLanguage(
                Language.valueOf(language), PageRequest.of(page, size)).getContent();
        return modelMapper.map(articles, new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }

    @Override
    public List<ArticleResponseDTO> recommendedList(Integer page, Integer size) {
        List<Article> recommendedList = articleRepository
                .findRecommendedArticles(PageRequest.of(page, size)).getContent();
        return modelMapper.map(recommendedList, new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }

    @Override
    public List<ArticleResponseDTO> searchByTitle(String title, Integer page, Integer size) {
        List<Article> articles = articleRepository.searchByTitle(title, PageRequest.of(page, size)).getContent();
        return modelMapper.map(articles, new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }

    @Override
    public List<ArticleResponseDTO> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Article> articleList = articleRepository.findAll(pageable).getContent();
        return modelMapper.map(articleList, new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }

    @Override
    public List<ArticleResponseDTO> findByPublisher(UUID createdById, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return modelMapper.map(articleRepository.findArticleByCreatedById(createdById, pageable),
                new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }


    @Override
    public List<ArticleResponseDTO> getByRegion(UUID regionID, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return modelMapper.map(articleRepository.findArticleByRegion(regionID, pageable),
                new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }

    @Override
    public List<ArticleResponseDTO> getAllBlocked(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return modelMapper.map(articleRepository.findArticleByStatusBlocked(pageable),
                new TypeToken<List<ArticleResponseDTO>>() {}.getType());
    }


    @Override
    public List<ArticleResponseDTO> getLatestNews(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishedDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        return modelMapper.map(articleRepository.findLatestNews(pageable),
                new TypeToken<List<ArticleResponseDTO>>() {}.getType());

    }

    @Override
    public ArticleResponseDTO updateById(UUID articleID, ArticleUpdateDTO updateDTO) {
        return null;
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
        try {
            article.setStatus(ArticleStatus.valueOf(status));
            articleRepository.save(article);
            return "Status successfully changed";
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Enum type not valid: " + status);
        }
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