package com.cephalea.backend.dto;

import com.cephalea.backend.entity.CrisisEntity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntensityCrudDto {
    @NotNull
    private LocalDateTime date;

    @Min(0)
    @Max(10)
    @NotBlank
    private Integer number;

    @NotNull
    private CrisisEntity crisis;
}
