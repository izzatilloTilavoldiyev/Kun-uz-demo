package com.company.kunuzdemo.repository;

import com.company.kunuzdemo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    //todo articleID fix
    @Query(value = "from comment c where c.article.id =: articleId and c.article.deleted = false")
    List<Comment> findCommentByArticle(@Param("articleId") UUID articleId);
}
