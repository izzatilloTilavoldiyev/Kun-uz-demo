package com.company.kunuzdemo.repository;

import com.company.kunuzdemo.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, UUID> {

    @Query("from likes l where l.status = 'LIKE' and l.article.id =:articleID")
    Page<Like> findLikeByArticleID(@Param("articleID") UUID articleID, Pageable pageable);

    @Query("from likes l where l.status = 'DISLIKE' and l.article.id =:articleID")
    Page<Like> findDislikeByArticleID(@Param("articleID") UUID articleID, Pageable pageable);
}
