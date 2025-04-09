package com.cephalea.backend.mapper;

import com.cephalea.backend.dto.ActivityAffectedCrudDto;
import com.cephalea.backend.dto.ActivityAffectedDto;
import com.cephalea.backend.entity.ActivityAffectedEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface ActivityAffectedDTOMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ActivityAffectedDto toDTO(ActivityAffectedEntity activityAffectedEntity);

    @Mapping(target = "name", source = "name")
    ActivityAffectedEntity toEntity(ActivityAffectedCrudDto activityAffectedCrudDto);
}
