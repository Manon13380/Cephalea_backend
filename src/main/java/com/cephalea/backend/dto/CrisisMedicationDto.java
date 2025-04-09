package com.cephalea.backend.dto;

import com.cephalea.backend.entity.CrisisEntity;
import com.cephalea.backend.entity.MedicationEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrisisMedicationDto {


    private UUID id;

    private CrisisEntity crisis;

    private MedicationEntity medication;

    private LocalDateTime dateTimeIntake;

}
