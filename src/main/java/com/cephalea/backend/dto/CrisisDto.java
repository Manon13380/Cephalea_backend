package com.cephalea.backend.dto;

import com.cephalea.backend.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrisisDto {
    private UUID id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String comment;

    private UserDto user;

    private Set<SoulagementDto> soulagements = new HashSet<>();

    private Set<ActivityAffectedDto> activities = new HashSet<>();

    private Set<PotentialTriggerDto> triggers = new HashSet<>();

    private Set<IntensityDto> intensities = new HashSet<>();

    private Set<CrisisMedicationDto> crisisMedication = new HashSet<>();

}
