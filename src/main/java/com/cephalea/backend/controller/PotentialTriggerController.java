package com.cephalea.backend.controller;


import com.cephalea.backend.dto.PotentialTriggerCrudDto;
import com.cephalea.backend.dto.PotentialTriggerdto;
import com.cephalea.backend.service.PotentialTriggerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/trigger")
    public ResponseEntity<PotentialTriggerdto> triggerPost(@Valid @RequestBody PotentialTriggerCrudDto potentialTriggerCrudDto) {
        log.debug("TriggerPost {}", potentialTriggerCrudDto);
        PotentialTriggerdto createdTrigger= potentialTriggerService.createTrigger(potentialTriggerCrudDto);
        return ResponseEntity.ok(createdTrigger);
    }

    @GetMapping("/triggers")
    public ResponseEntity<List<PotentialTriggerdto>> triggersGet() {
        log.debug("triggersGet");
        List<PotentialTriggerdto> triggerDTOList = potentialTriggerService.findAll();
        return ResponseEntity.ok(triggerDTOList);
    }

    @GetMapping("/trigger/{id}")
    public ResponseEntity<PotentialTriggerdto> triggerGet(@PathVariable UUID id) {
        log.debug("triggerGet");
        PotentialTriggerdto trigger = potentialTriggerService.findByUUID(id);
        return ResponseEntity.ok(trigger);
    }

    @PutMapping("/trigger/{id}")
    public ResponseEntity<PotentialTriggerdto> triggerPut(@PathVariable UUID id, @RequestBody PotentialTriggerCrudDto potentialTriggerCrudDto) {
        log.debug("triggerPut");
        PotentialTriggerdto updateTrigger= potentialTriggerService.updateTrigger(potentialTriggerCrudDto,id);
        return ResponseEntity.ok(updateTrigger);
    }

    @DeleteMapping("/trigger/{id}")
    public ResponseEntity<Void> triggerDelete(@PathVariable UUID id) {
        log.debug("REST request to delete trigger with ID {}", id);
        potentialTriggerService.deleteTrigger(id);
        return ResponseEntity.noContent().build();
    }
}
