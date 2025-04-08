package com.cephalea.backend.mapper;

import com.cephalea.backend.dto.SoulagementCrudDto;
import com.cephalea.backend.dto.SoulagementDto;
import com.cephalea.backend.dto.UserCrudDto;
import com.cephalea.backend.dto.UserDto;
import com.cephalea.backend.entity.SoulagementEntity;
import com.cephalea.backend.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface SoulagementDTOMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SoulagementDto toDTO(SoulagementEntity soulagementEntity);

    @Mapping(target = "name", source = "name")
    SoulagementEntity toEntity(SoulagementCrudDto soulagementCrudDto);
}
