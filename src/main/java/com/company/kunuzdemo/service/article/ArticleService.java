package com.company.kunuzdemo.service.article;

import com.company.kunuzdemo.dtos.request.ArticleCreateDTO;
import com.company.kunuzdemo.dtos.request.ArticleUpdateDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;
import com.company.kunuzdemo.entity.Article;

import java.util.List;
import java.util.UUID;

public interface ArticleService {

    Article getArticle(UUID articleID);

    ArticleResponseDTO create(ArticleCreateDTO articleCreateDTO);

    ArticleResponseDTO getByID(UUID articleID);

    List<ArticleResponseDTO> getByLanguage(String language, Integer page, Integer size);

    List<ArticleResponseDTO> recommendedList(Integer page, Integer size);

    List<ArticleResponseDTO> searchByTitle(String title, Integer page, Integer size);

    List<ArticleResponseDTO> findByPublisher(UUID createdById, Integer page, Integer size);

    List<ArticleResponseDTO> getAll(Integer page, Integer size);

    List<ArticleResponseDTO> getByRegion(UUID regionID, Integer page, Integer size);

    ArticleResponseDTO updateById(UUID articleID, ArticleUpdateDTO updateDTO);

    List<ArticleResponseDTO> getAllBlocked(Integer page, Integer size);

    List<ArticleResponseDTO> getLatestNews(Integer page, Integer size);

    String deleteById(UUID articleID);

    String deleteSelected(List<UUID> articleIDs);

    String changeStatus(UUID articleID, String status);
}
