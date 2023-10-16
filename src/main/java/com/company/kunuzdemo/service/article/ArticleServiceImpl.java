package com.company.kunuzdemo.service.article;


import com.company.kunuzdemo.controller.ArticleController;
import com.company.kunuzdemo.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleController articleController;
    private final ArticleRepository articleRepository;

}