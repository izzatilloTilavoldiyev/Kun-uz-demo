package com.company.kunuzdemo.repository;

import com.company.kunuzdemo.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    @Query("select a from article a where a.id =:articleID and not a.deleted")
    Optional<Article> findArticleByID(@Param("articleID") UUID articleID);

    boolean existsByTitle(@Param("title") String title);
}
