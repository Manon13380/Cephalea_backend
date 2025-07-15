package com.cephalea.backend.dto;


import com.cephalea.backend.enumeration.Period;
import lombok.*;


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

    private Boolean isTreatment;

    private UserDto user ;

}
