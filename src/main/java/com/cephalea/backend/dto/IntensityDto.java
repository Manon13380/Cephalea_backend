package com.cephalea.backend.dto;

import com.cephalea.backend.entity.CrisisEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntensityDto {

    private UUID id;

    private LocalDateTime date;

    private Integer number;

}
