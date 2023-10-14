package com.company.kunuzdemo.service.region;

import com.company.kunuzdemo.dtos.request.RegionCreateDTO;
import com.company.kunuzdemo.dtos.request.RegionUpdateDTO;
import com.company.kunuzdemo.dtos.response.RegionResponseDTO;
import com.company.kunuzdemo.entity.Region;
import com.company.kunuzdemo.exception.DataNotFoundException;
import com.company.kunuzdemo.repository.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class RegionServiceImplTest {

    @Mock
    private RegionRepository regionRepository;
    @Mock
    private ModelMapper modelMapper;
    private RegionService regionService;
    private Region region;
    private RegionCreateDTO regionCreateDTO;
    private List<RegionResponseDTO> regionResponseDTOS;
    private RegionUpdateDTO dto;
    private RegionResponseDTO responseDTO;
    private UUID regionID;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        regionService = new RegionServiceImpl(regionRepository, modelMapper);
        regionCreateDTO = new RegionCreateDTO("UZ", "RU", "EN");
        regionID = UUID.randomUUID();
    }

    @Test
    void create() {
        when(modelMapper.map(regionCreateDTO, Region.class)).thenReturn(region);
        when(regionRepository.save(region)).thenReturn(region);
        when(modelMapper.map(region, RegionResponseDTO.class)).thenReturn(responseDTO);

        RegionResponseDTO rez = regionService.create(regionCreateDTO);

        assertEquals(rez, responseDTO);
    }

    @Test
    void getByID() {

        when(modelMapper.map(regionRepository.findRegionById(regionID), RegionResponseDTO.class)).thenReturn(responseDTO);
        when(regionRepository.findRegionById(regionID)).thenReturn(Optional.of(region));
        RegionResponseDTO rez = regionService.getByID(regionID);
        assertEquals(rez, responseDTO);
    }

    @Test
    void update() {
        when(regionRepository.findById(regionID)).thenReturn(Optional.of(region));
        doNothing().when(modelMapper).map(dto, region);
        when(regionRepository.save(region)).thenReturn(region);
        when(modelMapper.map(region, RegionResponseDTO.class));

        RegionResponseDTO rez = regionService.update(regionID, dto);

    }

    @Test
    void getAll() {
        when(modelMapper.map(regionRepository.findAll(),
                new TypeToken<List<RegionResponseDTO>>() {}.getType())).thenReturn(regionResponseDTOS);
        List<RegionResponseDTO> rez = regionService.getAll();

        assertEquals(rez, regionResponseDTOS);

    }

    @Test
    void getAllVisible() {
        when(modelMapper.map(regionRepository.findAllVisible(),
                new TypeToken<List<RegionResponseDTO>>() {}.getType())).thenReturn(regionResponseDTOS);
        List<RegionResponseDTO> rez = regionService.getAllVisible();

        assertEquals(rez, regionResponseDTOS);
    }

    @Test
    void getAllUnVisible() {
    }

    @Test
    void deleteByID() {
    }

    @Test
    void deleteSelectedRegions() {
    }
}