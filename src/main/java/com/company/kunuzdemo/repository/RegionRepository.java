package com.company.kunuzdemo.repository;

import com.company.kunuzdemo.entity.Region;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegionRepository extends JpaRepository<Region, UUID> {

    @Query(value = "from region r where r.id =:regionID and r.visible = true ")
    Optional<Region> findRegionById(@Param("regionID") @NotNull UUID regionID);

    @Query(value = "from region r where r.visible = true ")
    List<Region> findAllVisible();

    @Query(value = "from region r where r.visible = false ")
    List<Region> findAllUnVisible();
}
