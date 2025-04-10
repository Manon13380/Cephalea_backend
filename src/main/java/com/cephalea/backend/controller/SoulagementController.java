package com.cephalea.backend.controller;

import com.cephalea.backend.dto.SoulagementCrudDto;
import com.cephalea.backend.dto.SoulagementDto;
import com.cephalea.backend.service.SoulagementService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class SoulagementController {

    private final SoulagementService soulagementService;

    public SoulagementController(SoulagementService soulagementService) {
        this.soulagementService = soulagementService;
    }

    @PostMapping("/soulagements")
    public ResponseEntity<SoulagementDto> SoulagementPost(@Valid @RequestBody SoulagementCrudDto soulagementCrudDto) {
        log.debug("SoulagementPost {}", soulagementCrudDto);
        SoulagementDto createdSoulagement = soulagementService.createSoulagement(soulagementCrudDto);
        return ResponseEntity.ok(createdSoulagement);
    }

    @GetMapping("/soulagements")
    public ResponseEntity<List<SoulagementDto>> soulagementsGet() {
        log.debug("soulagementsGet");
        List<SoulagementDto> soulagementDTOList = soulagementService.findAll();
        return ResponseEntity.ok(soulagementDTOList);
    }

    @GetMapping("/soulagements/{id}")
    public ResponseEntity<SoulagementDto> soulagementGet(@PathVariable UUID id) {
        log.debug("soulagementGet");
        SoulagementDto soulagement = soulagementService.findByUUID(id);
        return ResponseEntity.ok(soulagement);
    }

    @PutMapping("/soulagements/{id}")
    public ResponseEntity<SoulagementDto> soulagementPut(@PathVariable UUID id, @RequestBody SoulagementCrudDto soulagementCrudDto) {
        log.debug("soulagementPut");
        SoulagementDto updateSoulagement= soulagementService.updateSoulagement(soulagementCrudDto,id);
        return ResponseEntity.ok(updateSoulagement);
    }

    @DeleteMapping("/soulagements/{id}")
    public ResponseEntity<Void> soulagementDelete(@PathVariable UUID id) {
        log.debug("REST request to delete soulagement with ID {}", id);
        soulagementService.deleteSoulagement(id);
        return ResponseEntity.noContent().build();
    }
}
