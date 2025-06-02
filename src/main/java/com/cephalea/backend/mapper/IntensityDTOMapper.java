package com.cephalea.backend.mapper;


import com.cephalea.backend.dto.IntensityCrudDto;
import com.cephalea.backend.dto.IntensityDto;
import com.cephalea.backend.entity.IntensityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface IntensityDTOMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "number", source = "number")
    IntensityDto toDTO(IntensityEntity intensityEntity);

    @Mapping(target = "date", source = "date")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "crisis", source = "crisis")
    IntensityEntity toEntity(IntensityCrudDto intensityCrudDto);

}
