package com.company.kunuzdemo.service.article;

import com.company.kunuzdemo.dtos.request.ArticleCreateDTO;
import com.company.kunuzdemo.dtos.request.ArticleUpdateDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;

import java.util.List;
import java.util.UUID;

public interface ArticleService {

    ArticleResponseDTO create(ArticleCreateDTO articleCreateDTO);

    ArticleResponseDTO getByID(UUID articleID);

    List<ArticleResponseDTO> getAll(Integer page, Integer size);

    List<ArticleResponseDTO> findByPublisher(UUID createdById, Integer page, Integer size);


    List<ArticleResponseDTO> getByRegion(UUID regionID, Integer page, Integer size);

    ArticleResponseDTO updateById(UUID articleID, ArticleUpdateDTO updateDTO);

    List<ArticleResponseDTO> getAllBlocked(Integer page, Integer size);

    List<ArticleResponseDTO> getLatestNews(Integer page, Integer size);

    String deleteById(UUID articleID);

    String deleteSelected(List<UUID> articleIDs);
}
