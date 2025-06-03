package com.cephalea.backend.controller;


import com.cephalea.backend.dto.MedicationCrudDto;
import com.cephalea.backend.dto.MedicationDto;
import com.cephalea.backend.service.MedicationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping("/medications")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MedicationDto> medicationPost(@Valid @RequestBody MedicationCrudDto medicationCrudDto) {
        log.debug("medicationPost {}", medicationCrudDto);
        MedicationDto createdMedication = medicationService.createMedication(medicationCrudDto);
        return ResponseEntity.ok(createdMedication);
    }

    @GetMapping("/medications")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MedicationDto>> medicationsGet() {
        log.debug("medicationsGet");
        List<MedicationDto> medicationsDTOList = medicationService.findAllForCurrentUserTraitement();
        return ResponseEntity.ok(medicationsDTOList);
    }

    @GetMapping("/medications/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MedicationDto> medicationGet(@PathVariable UUID id) {
        log.debug("medicationGet");
        MedicationDto medication = medicationService.findByUUID(id);
        return ResponseEntity.ok(medication);
    }

    @PatchMapping("/medications/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MedicationDto> userPatch(@PathVariable UUID id, @RequestBody MedicationCrudDto medicationCrudDto) {
        log.debug("medicationsPut");
        MedicationDto updateMedication= medicationService.updateMedication(medicationCrudDto,id);
        return ResponseEntity.ok(updateMedication);
    }

    @DeleteMapping("/medications/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> medicationDelete(@PathVariable UUID id) {
        log.debug("REST request to delete medication with ID {}", id);
        medicationService.deleteMedication(id);
        return ResponseEntity.noContent().build();
    }

}
