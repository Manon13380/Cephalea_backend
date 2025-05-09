package com.cephalea.backend.controller;


import com.cephalea.backend.dto.IntensityCrudDto;
import com.cephalea.backend.dto.IntensityDto;
import com.cephalea.backend.service.IntensityService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class IntensityController {
    private final IntensityService intensityService;

    public IntensityController(IntensityService intensityService) {
        this.intensityService = intensityService;
    }

    @PostMapping("/intensities")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IntensityDto> intensityPost(@Valid @RequestBody IntensityCrudDto intensityCrudDto, @RequestParam UUID id) {
        log.debug("intensitiesPost {}", intensityCrudDto);
        IntensityDto createdIntensity = intensityService.createIntensity(intensityCrudDto, id);
        return ResponseEntity.ok(createdIntensity);
    }

    @GetMapping("/intensities")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<IntensityDto>> intensitiesGet() {
        log.debug("intensitiesGet");
        List<IntensityDto> intensitiesDTOList = intensityService.findAll();
        return ResponseEntity.ok(intensitiesDTOList);
    }

    @GetMapping("/intensities/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IntensityDto> intensityGet(@PathVariable UUID id) {
        log.debug("intensityGet");
        IntensityDto intensity = intensityService.findByUUID(id);
        return ResponseEntity.ok(intensity);
    }

    @PatchMapping("/intensities/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<IntensityDto> userPatch(@PathVariable UUID id, @RequestBody IntensityCrudDto intensityCrudDto) {
        log.debug("intensitiesPut");
        IntensityDto updateIntensity= intensityService.updateIntensity(intensityCrudDto,id);
        return ResponseEntity.ok(updateIntensity);
    }

    @DeleteMapping("/intensities/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> intensityDelete(@PathVariable UUID id) {
        log.debug("REST request to delete intensity with ID {}", id);
        intensityService.deleteIntensity(id);
        return ResponseEntity.noContent().build();
    }

}
