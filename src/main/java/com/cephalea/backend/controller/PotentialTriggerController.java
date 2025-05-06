package com.cephalea.backend.controller;


import com.cephalea.backend.dto.PotentialTriggerCrudDto;
import com.cephalea.backend.dto.PotentialTriggerDto;
import com.cephalea.backend.service.PotentialTriggerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class PotentialTriggerController {

    private final PotentialTriggerService potentialTriggerService;

    public PotentialTriggerController(PotentialTriggerService potentialTriggerService) {
        this.potentialTriggerService = potentialTriggerService;
    }

    @PostMapping("/triggers")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PotentialTriggerDto> triggerPost(@Valid @RequestBody PotentialTriggerCrudDto potentialTriggerCrudDto) {
        log.debug("TriggerPost {}", potentialTriggerCrudDto);
        PotentialTriggerDto createdTrigger= potentialTriggerService.createTrigger(potentialTriggerCrudDto);
        return ResponseEntity.ok(createdTrigger);
    }

    @GetMapping("/triggers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PotentialTriggerDto>> triggersGet() {
        log.debug("triggersGet");
        List<PotentialTriggerDto> triggerDTOList = potentialTriggerService.findAll();
        return ResponseEntity.ok(triggerDTOList);
    }

    @GetMapping("/triggers/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PotentialTriggerDto> triggerGet(@PathVariable UUID id) {
        log.debug("triggerGet");
        PotentialTriggerDto trigger = potentialTriggerService.findByUUID(id);
        return ResponseEntity.ok(trigger);
    }

    @PutMapping("/triggers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<PotentialTriggerDto> triggerPut(@PathVariable UUID id, @RequestBody PotentialTriggerCrudDto potentialTriggerCrudDto) {
        log.debug("triggerPut");
        PotentialTriggerDto updateTrigger= potentialTriggerService.updateTrigger(potentialTriggerCrudDto,id);
        return ResponseEntity.ok(updateTrigger);
    }

    @DeleteMapping("/triggers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> triggerDelete(@PathVariable UUID id) {
        log.debug("REST request to delete trigger with ID {}", id);
        potentialTriggerService.deleteTrigger(id);
        return ResponseEntity.noContent().build();
    }
}
