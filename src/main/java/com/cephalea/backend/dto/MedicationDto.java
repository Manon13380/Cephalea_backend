package com.cephalea.backend.dto;

import com.cephalea.backend.entity.CrisisMedicationEntity;
import com.cephalea.backend.entity.UserEntity;
import com.cephalea.backend.enumeration.Period;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationDto {

    private UUID id;

    private String name;

    private String dosage;

    private Integer quantity;

    private Period periodQuantity;

    private Integer duration;

    private Period periodDuration;

    private String interval;

    private Integer maximum;

    private Period periodMaximum;

    private Boolean isDelete;

    private Boolean isAlarm;

    private UserEntity user;

    private Set<CrisisMedicationEntity> crisisMedication = new HashSet<>();
}
