package com.cephalea.backend.dto;

import com.cephalea.backend.entity.CrisisMedicationEntity;
import com.cephalea.backend.entity.UserEntity;
import com.cephalea.backend.enumeration.Period;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicationCrudDto {
    @NotBlank(message = "Le nom du médicament ne doit pas être vide")
    private String name;

    @NotBlank(message = "Le dosage ne doit pas être vide")
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

    @NotNull
    private UserEntity user;

    private Set<CrisisMedicationEntity> crisisMedicationEntities = new HashSet<>();

}
