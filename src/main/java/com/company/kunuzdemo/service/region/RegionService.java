package com.company.kunuzdemo.service.region;

import com.company.kunuzdemo.dtos.request.RegionCreateDTO;
import com.company.kunuzdemo.dtos.request.RegionUpdateDTO;
import com.company.kunuzdemo.dtos.response.RegionResponseDTO;
import com.company.kunuzdemo.entity.Region;

import java.util.List;
import java.util.UUID;

public interface RegionService {

    Region getRegion(UUID regionID);

    RegionResponseDTO create(RegionCreateDTO dto);

    RegionResponseDTO getByID(UUID regionID);

    RegionResponseDTO update(UUID regionID, RegionUpdateDTO dto);

    List<RegionResponseDTO> getAll();

    List<RegionResponseDTO> getAllVisible();

    List<RegionResponseDTO> getAllUnVisible();

    void deleteByID(UUID regionID);

    void deleteSelectedRegions(List<UUID> regionIDs);
}
