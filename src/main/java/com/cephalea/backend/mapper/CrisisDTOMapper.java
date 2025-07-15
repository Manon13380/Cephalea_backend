package com.cephalea.backend.mapper;

import com.cephalea.backend.dto.CrisisCrudDto;
import com.cephalea.backend.dto.CrisisDto;
import com.cephalea.backend.entity.CrisisEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { CrisisMedicationDTOMapper.class })
@SuppressWarnings("UnmappedTargetProperties")
public interface CrisisDTOMapper {

    @Mapping(target = "id" , source = "id")
    @Mapping(target = "startDate" , source = "startDate")
    @Mapping(target = "endDate" , source = "endDate")
    @Mapping(target = "comment" , source = "comment")
    @Mapping(target = "user" , source = "user")
    @Mapping(target = "soulagements" , source = "soulagements")
    @Mapping(target = "activities" , source = "activities")
    @Mapping(target = "triggers" , source = "triggers")
    @Mapping(target = "intensities" , source = "intensities")
    @Mapping(target = "crisisMedication" , source = "crisisMedication")
    CrisisDto toDTO(CrisisEntity crisisEntity);

    @Mapping(target = "startDate" , source = "startDate")
    @Mapping(target = "endDate" , source = "endDate")
    @Mapping(target = "comment" , source = "comment")
    @Mapping(target = "user" , source = "user")
    @Mapping(target = "soulagements" , source = "soulagements")
    @Mapping(target = "activities" , source = "activities")
    @Mapping(target = "triggers" , source = "triggers")
    @Mapping(target = "intensities" , source = "intensities")
    @Mapping(target = "crisisMedication" , source = "crisisMedication")
    CrisisEntity toEntity(CrisisCrudDto crisisCrudDto);



}
