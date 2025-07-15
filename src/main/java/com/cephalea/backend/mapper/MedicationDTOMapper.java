package com.cephalea.backend.mapper;

import com.cephalea.backend.dto.MedicationCrudDto;
import com.cephalea.backend.dto.MedicationDto;
import com.cephalea.backend.entity.MedicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface MedicationDTOMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "dosage", source = "dosage")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "periodQuantity", source = "periodQuantity")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "periodDuration", source = "periodDuration")
    @Mapping(target = "interval", source = "interval")
    @Mapping(target = "maximum", source = "maximum")
    @Mapping(target = "periodMaximum", source = "periodMaximum")
    @Mapping(target = "isDelete", source = "isDelete")
    @Mapping(target = "isAlarm", source = "isAlarm")
    @Mapping(target = "isTreatment", source = "isTreatment")
    @Mapping(target = "user", source = "user")
    MedicationDto toDTO(MedicationEntity medicationEntity);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "dosage", source = "dosage")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "periodQuantity", source = "periodQuantity")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "periodDuration", source = "periodDuration")
    @Mapping(target = "interval", source = "interval")
    @Mapping(target = "maximum", source = "maximum")
    @Mapping(target = "periodMaximum", source = "periodMaximum")
    @Mapping(target = "isDelete", source = "isDelete")
    @Mapping(target = "isAlarm", source = "isAlarm")
    @Mapping(target = "isTreatment", source = "isTreatment")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "crisisMedication", source = "crisisMedication")
    MedicationEntity toEntity(MedicationCrudDto medicationCrudDto);



}


