package com.cephalea.backend.service;

import com.cephalea.backend.dto.ActivityAffectedCrudDto;
import com.cephalea.backend.dto.ActivityAffectedDto;
import com.cephalea.backend.entity.ActivityAffectedEntity;
import com.cephalea.backend.mapper.ActivityAffectedDTOMapper;
import com.cephalea.backend.repository.ActivityAffectedRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class ActivityAffectedService {

    private final ActivityAffectedRepository activityAffectedRepository;
    private final ActivityAffectedDTOMapper activityAffectedDTOMapper;

    @Autowired
    public ActivityAffectedService(ActivityAffectedRepository activityAffectedRepository, ActivityAffectedDTOMapper activityAffectedDTOMapper) {
        this.activityAffectedRepository = activityAffectedRepository;
        this.activityAffectedDTOMapper = activityAffectedDTOMapper;
    }

    //Read all Activities
    @Transactional(readOnly = true)
    public List<ActivityAffectedDto> findAll() {
        log.debug("Find all Activities Affected");
        List<ActivityAffectedEntity> activities = activityAffectedRepository.findAll();
        List<ActivityAffectedDto> activityAffectedDtos = activities.stream().map(activityAffectedDTOMapper::toDTO).toList();
        log.debug("FindAll- Found {} activities", activityAffectedDtos.size());
        log.debug("FindAll- got list {}", activityAffectedDtos);
        return activityAffectedDtos;
    }

    //Read One Activity
    @Transactional(readOnly = true)
    public ActivityAffectedDto findByUUID(UUID id) {
        log.debug("Find Activity by UUID {}", id);
        return activityAffectedRepository.findById(id)
                .map(activityAffectedDTOMapper::toDTO)
                .map(activityAffectedDto -> {
                    log.debug("Find activity by UUID {}", activityAffectedDto);
                    return activityAffectedDto;
                })
                .orElseThrow(() -> new EntityNotFoundException("activity not found with UUID: " + id));
    }

    //Create Activity
    public ActivityAffectedDto createActivity(ActivityAffectedCrudDto activityAffectedCrudDto) {
        log.debug("Create activity {}", activityAffectedCrudDto);
        if (activityAffectedRepository.existsByName(activityAffectedCrudDto.getName())) {
            log.debug("Activity with name {} already exists", activityAffectedCrudDto.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Activity with name " + activityAffectedCrudDto.getName() + " already exists.");
        }

        //Map DTO to entity
        ActivityAffectedEntity activityAffectedEntity = activityAffectedDTOMapper.toEntity(activityAffectedCrudDto);


        //Save Activity
        ActivityAffectedEntity savedActivityEntity = activityAffectedRepository.save(activityAffectedEntity);
        log.debug("Create activity {}", savedActivityEntity);
        return activityAffectedDTOMapper.toDTO(savedActivityEntity);
    }

    //Update Activity
    public ActivityAffectedDto updateActivity(ActivityAffectedCrudDto activityAffectedCrudDto, UUID id) {
        log.debug("Update activity {}", activityAffectedCrudDto);

        //Find Existing by Name
        ActivityAffectedEntity activityToUpdate = activityAffectedRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with ID: " + id));


        if (!activityAffectedCrudDto.getName().equals(activityToUpdate.getName()) && activityAffectedRepository.existsByName(activityAffectedCrudDto.getName())) {
            log.error("Activity with name {} already exists", activityAffectedCrudDto.getName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Activity with name " + activityAffectedCrudDto.getName() + " already exists.");
        }


        activityToUpdate.setName(activityAffectedCrudDto.getName());


        ActivityAffectedEntity updatedActivityEntity = activityAffectedRepository.save(activityToUpdate);
        log.debug("Update activity {}", updatedActivityEntity);
        return activityAffectedDTOMapper.toDTO(updatedActivityEntity);
    }

    //Delete Activity
    public void deleteActivity(UUID id) {
        log.debug("Delete activity {}", id);
        if (!activityAffectedRepository.existsById(id)) {
            throw new EntityNotFoundException("Activity not found with ID: " + id);
        }
        activityAffectedRepository.deleteById(id);
        log.debug("Delete activity {}", id);
    }
}
