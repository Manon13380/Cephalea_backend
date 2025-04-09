package com.cephalea.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PotentialTriggerDto {
    private UUID id;
    private String name;
}
