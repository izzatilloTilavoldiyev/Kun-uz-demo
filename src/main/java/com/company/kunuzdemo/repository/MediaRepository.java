package com.company.kunuzdemo.repository;

import com.company.kunuzdemo.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {

//    @Query("select m from media m where m.id =:ID and not m.deleted")
//    Optional<Media> findMediaByID(@Param("ID") Long ID);
}
