package com.cephalea.backend.mapper;


import com.cephalea.backend.dto.PotentialTriggerCrudDto;
import com.cephalea.backend.dto.PotentialTriggerDto;
import com.cephalea.backend.entity.PotentialTriggerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface PotentialTriggerDTOMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PotentialTriggerDto toDTO(PotentialTriggerEntity potentialTriggerEntity);

    @Mapping(target = "name", source = "name")
    PotentialTriggerEntity toEntity(PotentialTriggerCrudDto potentialTriggerCrudDto);
}
