package com.cephalea.backend.controller;


import com.cephalea.backend.dto.MedicationCrudDto;
import com.cephalea.backend.dto.MedicationDto;
import com.cephalea.backend.service.MedicationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MedicationDto> medicationPost(@Valid @RequestBody MedicationCrudDto medicationCrudDto) {
        log.debug("medicationPost {}", medicationCrudDto);
        MedicationDto createdMedication = medicationService.createMedication(medicationCrudDto);
        return ResponseEntity.ok(createdMedication);
    }

    @GetMapping("/medications")
    public ResponseEntity<List<MedicationDto>> medicationsGet() {
        log.debug("medicationsGet");
        List<MedicationDto> medicationsDTOList = medicationService.findAll();
        return ResponseEntity.ok(medicationsDTOList);
    }

    @GetMapping("/medications/{id}")
    public ResponseEntity<MedicationDto> medicationGet(@PathVariable UUID id) {
        log.debug("medicationGet");
        MedicationDto medication = medicationService.findByUUID(id);
        return ResponseEntity.ok(medication);
    }

    @PatchMapping("/medications/{id}")
    public ResponseEntity<MedicationDto> userPatch(@PathVariable UUID id, @RequestBody MedicationCrudDto medicationCrudDto) {
        log.debug("medicationsPut");
        MedicationDto updateMedication= medicationService.updateMedication(medicationCrudDto,id);
        return ResponseEntity.ok(updateMedication);
    }

}
