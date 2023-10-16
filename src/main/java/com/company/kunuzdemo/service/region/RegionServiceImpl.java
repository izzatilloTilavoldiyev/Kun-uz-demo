package com.company.kunuzdemo.service.region;

import com.company.kunuzdemo.dtos.request.RegionCreateDTO;
import com.company.kunuzdemo.dtos.request.RegionUpdateDTO;
import com.company.kunuzdemo.dtos.response.RegionResponseDTO;
import com.company.kunuzdemo.entity.Region;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService{

    private final RegionRepository regionRepository;
    private final ModelMapper modelMapper;

    @Override
    public Region getRegion(UUID regionID) {
        return getRegionByID(regionID);
    }

    @Override
    public RegionResponseDTO create(RegionCreateDTO dto) {
        Region mappedRegion = modelMapper.map(dto, Region.class);
        Region savedRegion = regionRepository.save(mappedRegion);
        return modelMapper.map(savedRegion, RegionResponseDTO.class);
    }
    
    @Override
    public RegionResponseDTO getByID(UUID regionID) {
        return modelMapper.map(getRegionByID(regionID), RegionResponseDTO.class);
    }

    @Override
    public RegionResponseDTO update(UUID regionID, RegionUpdateDTO dto) {
        Region region = regionRepository.findById(regionID).orElseThrow(
                () -> new DataNotFoundException("Region not found with ID: " + regionID)
        );
        modelMapper.map(dto, region);
        Region savedRegion = regionRepository.save(region);
        return modelMapper.map(savedRegion, RegionResponseDTO.class);
    }

    @Override
    public List<RegionResponseDTO> getAll() {
        return modelMapper.map(regionRepository.findAll(), new TypeToken<List<RegionResponseDTO>>() {}.getType());
    }

    @Override
    public List<RegionResponseDTO> getAllVisible() {
        return modelMapper.map(regionRepository.findAllVisible(), new TypeToken<List<RegionResponseDTO>>() {}.getType());
    }

    @Override
    public List<RegionResponseDTO> getAllUnVisible() {
        return modelMapper.map(regionRepository.findAllUnVisible(), new TypeToken<List<RegionResponseDTO>>() {}.getType());
    }

    @Override
    public void deleteByID(UUID regionID) {
        if (!regionRepository.existsById(regionID))
            throw new DataNotFoundException("Region not found with ID: " + regionID);
        regionRepository.deleteById(regionID);
    }

    @Override
    public void deleteSelectedRegions(List<UUID> regionIDs) {
        for (UUID regionID : regionIDs) {
            deleteByID(regionID);
        }
    }

    private Region getRegionByID(UUID regionID) {
        return regionRepository.findRegionById(regionID).orElseThrow(
                () -> new DataNotFoundException("Region not found with ID: " + regionID)
        );
    }
}