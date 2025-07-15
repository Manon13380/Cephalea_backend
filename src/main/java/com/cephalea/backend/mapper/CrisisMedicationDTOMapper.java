package com.cephalea.backend.mapper;

import com.cephalea.backend.dto.CrisisMedicationCrudDto;
import com.cephalea.backend.dto.CrisisMedicationDto;
import com.cephalea.backend.entity.CrisisMedicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { MedicationDTOMapper.class })
@SuppressWarnings("UnmappedTargetProperties")
public interface CrisisMedicationDTOMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "dateTimeIntake", source = "dateTimeIntake")
    @Mapping(target = "medication", source = "medication")
    CrisisMedicationDto toDTO(CrisisMedicationEntity crisisMedicationEntity);

    @Mapping(target = "dateTimeIntake", source = "dateTimeIntake")
    CrisisMedicationEntity toEntity(CrisisMedicationCrudDto crisisMedicationCrudDto);


}
