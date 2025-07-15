package com.cephalea.backend.dto;

import com.cephalea.backend.entity.CrisisEntity;
import com.cephalea.backend.entity.MedicationEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrisisMedicationCrudDto {

    @NotNull
//    @NotBlank(message = "La date ne peut pas être vide")
    private LocalDateTime dateTimeIntake;
}
