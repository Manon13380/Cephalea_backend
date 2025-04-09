package com.cephalea.backend.mapper;

import com.cephalea.backend.dto.ActivityAffectedCrudDto;
import com.cephalea.backend.dto.ActivityAffectedDto;
import com.cephalea.backend.dto.PotentialTriggerCrudDto;
import com.cephalea.backend.dto.PotentialTriggerdto;
import com.cephalea.backend.entity.ActivityAffectedEntity;
import com.cephalea.backend.entity.PotentialtriggerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface PotentialTriggerDTOMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PotentialTriggerdto toDTO(PotentialtriggerEntity potentialtriggerEntity);

    @Mapping(target = "name", source = "name")
    PotentialtriggerEntity toEntity(PotentialTriggerCrudDto potentialTriggerCrudDto);
}
