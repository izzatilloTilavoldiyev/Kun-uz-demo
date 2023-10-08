package com.company.kunuzdemo.controller;


import com.company.kunuzdemo.dtos.request.RegionCreateDTO;
import com.company.kunuzdemo.dtos.request.RegionUpdateDTO;
import com.company.kunuzdemo.dtos.response.RegionResponseDTO;
import com.company.kunuzdemo.service.region.RegionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/region")
public class RegionController {

    private final RegionService regionService;

    @PostMapping
    public ResponseEntity<RegionResponseDTO> create(
            @Valid @RequestBody RegionCreateDTO createDTO
    ) {
        RegionResponseDTO regionDTO = regionService.create(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(regionDTO);
    }

    @GetMapping("/{regionID}")
    public ResponseEntity<RegionResponseDTO> getById(
            @PathVariable @NotNull UUID regionID
    ) {
        return ResponseEntity.ok(regionService.getByID(regionID));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<RegionResponseDTO>> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/get-all-visible")
    public ResponseEntity<List<RegionResponseDTO>> getAllVisible() {
        return ResponseEntity.ok(regionService.getAllVisible());
    }

    @GetMapping("/get-all-un-visible")
    public ResponseEntity<List<RegionResponseDTO>> getAllUnVisible() {
        return ResponseEntity.ok(regionService.getAllUnVisible());
    }

    @PutMapping("/{regionID}")
    public ResponseEntity<RegionResponseDTO> update(
            @PathVariable @NotNull UUID regionID,
            @Valid @RequestBody RegionUpdateDTO updateDTO
    ) {
        return ResponseEntity.ok(regionService.update(regionID, updateDTO));
    }

    @DeleteMapping("/{regionID}")
    public ResponseEntity<String> delete(
            @PathVariable @NotNull UUID regionID
    ) {
        regionService.deleteByID(regionID);
        return ResponseEntity.ok("Successfully deleted!");
    }

}