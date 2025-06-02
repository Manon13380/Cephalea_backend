package com.cephalea.backend.controller;

import com.cephalea.backend.dto.CrisisCrudDto;
import com.cephalea.backend.dto.CrisisDto;
import com.cephalea.backend.service.CrisisService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class CrisisController {

    private final CrisisService crisisService;

    public CrisisController(CrisisService crisisService) {
        this.crisisService = crisisService;
    }

    @PostMapping("/crisis")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CrisisDto> crisisPost(@Valid @RequestBody CrisisCrudDto crisisCrudDto, Authentication authentication, @RequestParam int painIntensity) {
        log.debug("crisisPost {}", crisisCrudDto);
        String userName = authentication.getName();
        CrisisDto createdCrisis = crisisService.createCrisis(crisisCrudDto, painIntensity, userName);
        return ResponseEntity.ok(createdCrisis);
    }

    @GetMapping("/crisis")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CrisisDto>> crisisGet() {
        log.debug("crisisGet");
        List<CrisisDto> crisisDTOList = crisisService.findAll();
        return ResponseEntity.ok(crisisDTOList);
    }

    @GetMapping("/crisis/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CrisisDto> crisisGetOne(@PathVariable UUID id) {
        log.debug("crisisGetOne");
        CrisisDto crisis = crisisService.findByUUID(id);
        return ResponseEntity.ok(crisis);
    }

    @PatchMapping("/crisis/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CrisisDto> userPatch(@PathVariable UUID id, @RequestBody CrisisCrudDto crisisCrudDto) {
        log.debug("crisisPut");
        CrisisDto updateCrisis= crisisService.updateCrisis(crisisCrudDto,id);
        return ResponseEntity.ok(updateCrisis);
    }

    @DeleteMapping("/crisis/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> crisisDelete(@PathVariable UUID id) {
        log.debug("REST request to delete crisis with ID {}", id);
        crisisService.deleteCrisis(id);
        return ResponseEntity.noContent().build();
    }
}
