package com.cephalea.backend.mapper;

import com.cephalea.backend.dto.CrisisMedicationCrudDto;
import com.cephalea.backend.dto.CrisisMedicationDto;
import com.cephalea.backend.entity.CrisisMedicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface CrisisMedicationDTOMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "crisis", source = "crisis")
    @Mapping(target = "medication", source = "medication")
    @Mapping(target = "dateTimeIntake", source = "dateTimeIntake")
    CrisisMedicationDto toDTO(CrisisMedicationEntity crisisMedicationEntity);

    @Mapping(target = "crisis", source = "crisis")
    @Mapping(target = "medication", source = "medication")
    @Mapping(target = "dateTimeIntake", source = "dateTimeIntake")
    CrisisMedicationEntity toEntity(CrisisMedicationCrudDto crisisMedicationCrudDto);


}
