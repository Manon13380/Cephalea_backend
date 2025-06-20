package com.cephalea.backend.dto;

import com.cephalea.backend.entity.*;
import lombok.*;

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

    private Date startDate;

    private Date endDate;

    private String comment;

    private UserDto user;

    private Set<SoulagementEntity> soulagements = new HashSet<>();

    private Set<ActivityAffectedEntity> activities = new HashSet<>();

    private Set<PotentialTriggerEntity> triggers = new HashSet<>();

    private Set<IntensityDto> intensities = new HashSet<>();

    private Set<CrisisMedicationEntity> crisisMedication = new HashSet<>();

}
