package com.cephalea.backend.dto;

import lombok.*;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityAffectedDto {
    private UUID id;
    private String name;
}
