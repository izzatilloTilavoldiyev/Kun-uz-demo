package com.company.kunuzdemo.repository;

import com.company.kunuzdemo.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    @Query("select a from article a where a.id =:articleID and not a.deleted")
    Optional<Article> findArticleByID(@Param("articleID") UUID articleID);

    @Query(value = """
           select * from article a where lower(a.title) like 
           lower(concat('%', :title, '%')) and a.status = 'PUBLISHED' and not a.deleted
           """, nativeQuery = true)
    Page<Article> searchByTitle(@Param("title") String title, Pageable pageable);

    @Query(value = "from article a where a.createdBy.id =:createdById and a.createdBy.role = 'PUBLISHER'")
    List<Article> findArticleByCreatedById(@Param("createdById")UUID createdById, Pageable pageable);

    @Query(value = "from article a where a.status = 'BLOCKED'")
    List<Article> findArticleByStatusBlocked(Pageable pageable);

    @Query(value = "from article a where a.region.id =:regionID and a.region.visible = true ")
    List<Article> findArticleByRegion(@Param("regionID")UUID regionID, Pageable pageable);


//    List<Article> findLatesArticle(Pageable pageable);

    boolean existsByTitle(@Param("title") String title);

}
