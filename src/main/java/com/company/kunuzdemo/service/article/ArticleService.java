package com.company.kunuzdemo.service.article;

import com.company.kunuzdemo.dtos.request.ArticleCreateDTO;
import com.company.kunuzdemo.dtos.response.ArticleResponseDTO;

import java.util.UUID;

public interface ArticleService {

    ArticleResponseDTO create(ArticleCreateDTO articleCreateDTO);

    ArticleResponseDTO getByID(UUID articleID);
}
