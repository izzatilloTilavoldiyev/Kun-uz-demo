package com.company.kunuzdemo.repository;

import com.company.kunuzdemo.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
