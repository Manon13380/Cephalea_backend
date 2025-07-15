package com.cephalea.backend.controller;


import com.cephalea.backend.dto.CrisisMedicationCrudDto;
import com.cephalea.backend.dto.CrisisMedicationDto;
import com.cephalea.backend.service.CrisisMedicationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class CrisisMedicationController {
    private final CrisisMedicationService crisisMedicationService;

    public CrisisMedicationController(CrisisMedicationService crisisMedicationService) {
        this.crisisMedicationService = crisisMedicationService;
    }

    @PostMapping("/crisis/{id}/crisisMedications")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CrisisMedicationDto> crisisMedicationPost(@Valid @RequestBody CrisisMedicationCrudDto crisisMedicationCrudDto, @PathVariable UUID id , @RequestParam UUID medicationId ) {
        log.debug("crisisMedicationsPost {}", crisisMedicationCrudDto);
        CrisisMedicationDto createdCrisisMedication = crisisMedicationService.createCrisisMedication(crisisMedicationCrudDto, id ,medicationId);
        return ResponseEntity.ok(createdCrisisMedication);
    }

    @GetMapping("/crisisMedications")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CrisisMedicationDto>> crisisMedicationsGet() {
        log.debug("crisisMedicationsGet");
        List<CrisisMedicationDto> crisisMedicationsDTOList = crisisMedicationService.findAll();
        return ResponseEntity.ok(crisisMedicationsDTOList);
    }

    @GetMapping("/crisisMedications/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CrisisMedicationDto> crisisMedicationGetOne(@PathVariable UUID id) {
        log.debug("crisisMedicationGetOne");
        CrisisMedicationDto crisisMedication = crisisMedicationService.findByUUID(id);
        return ResponseEntity.ok(crisisMedication);
    }

    @PatchMapping("/crisis/{id}/crisisMedications/{crisisMedicationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CrisisMedicationDto> userPatch(@PathVariable UUID id, @PathVariable UUID crisisMedicationId, @RequestBody CrisisMedicationCrudDto crisisMedicationCrudDto, @RequestParam UUID medicationId) {
        log.debug("crisisMedicationPatch");
        CrisisMedicationDto updateCrisisMedication= crisisMedicationService.updateCrisisMedication(crisisMedicationCrudDto,id,crisisMedicationId,medicationId);
        return ResponseEntity.ok(updateCrisisMedication);
    }

    @DeleteMapping("/crisisMedications/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> crisisMedicationDelete(@PathVariable UUID id) {
        log.debug("REST request to delete crisisMedication with ID {}", id);
        crisisMedicationService.deleteCrisisMedication(id);
        return ResponseEntity.noContent().build();
    }

}
