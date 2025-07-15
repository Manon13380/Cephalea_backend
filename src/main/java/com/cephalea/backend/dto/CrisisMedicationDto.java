package com.cephalea.backend.dto;

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

    private MedicationDto medication;

    private LocalDateTime dateTimeIntake;

}
