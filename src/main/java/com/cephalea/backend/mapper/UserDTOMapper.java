package com.cephalea.backend.mapper;

import com.cephalea.backend.dto.UserCrudDto;
import com.cephalea.backend.dto.UserDto;
import com.cephalea.backend.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@SuppressWarnings("UnmappedTargetProperties")
public interface UserDTOMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "doctor", source = "doctor")
    @Mapping(target = "neurologist", source = "neurologist")
    @Mapping(target = "role" , source = "role")
    UserDto toDTO(UserEntity userEntity);



    @Mapping(target = "name", source = "name")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "doctor", source = "doctor")
    @Mapping(target = "neurologist", source = "neurologist")
    @Mapping(target = "role" , source = "role")
    UserEntity toEntity(UserCrudDto userCrudDto);
}
