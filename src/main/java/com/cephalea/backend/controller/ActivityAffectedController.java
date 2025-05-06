package com.cephalea.backend.controller;

import com.cephalea.backend.dto.ActivityAffectedCrudDto;
import com.cephalea.backend.dto.ActivityAffectedDto;
import com.cephalea.backend.service.ActivityAffectedService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class ActivityAffectedController {

    private final ActivityAffectedService activityAffectedService;

    public ActivityAffectedController(ActivityAffectedService activityAffectedService) {
        this.activityAffectedService = activityAffectedService;
    }

    @PostMapping("/activities")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ActivityAffectedDto> ActivityPost(@Valid @RequestBody ActivityAffectedCrudDto activityAffectedCrudDto) {
        log.debug("ActivityPost {}", activityAffectedCrudDto);
        ActivityAffectedDto createdActivity= activityAffectedService.createActivity(activityAffectedCrudDto);
        return ResponseEntity.ok(createdActivity);
    }

    @GetMapping("/activities")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ActivityAffectedDto>> activitiesGet() {
        log.debug("get all activities");
        List<ActivityAffectedDto> activityDTOList = activityAffectedService.findAll();
        return ResponseEntity.ok(activityDTOList);
    }

    @GetMapping("/activities/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ActivityAffectedDto> activityGet(@PathVariable UUID id) {
        log.debug("activityGet");
        ActivityAffectedDto activity = activityAffectedService.findByUUID(id);
        return ResponseEntity.ok(activity);
    }

    @PutMapping("/activities/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ActivityAffectedDto> activityPut(@PathVariable UUID id, @RequestBody ActivityAffectedCrudDto activityAffectedCrudDto){
        log.debug("activityPut");
        ActivityAffectedDto updateActivity= activityAffectedService.updateActivity(activityAffectedCrudDto,id);
        return ResponseEntity.ok(updateActivity);
    }

    @DeleteMapping("/activities/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> activityDelete(@PathVariable UUID id) {
        log.debug("REST request to delete activity with ID {}", id);
        activityAffectedService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
