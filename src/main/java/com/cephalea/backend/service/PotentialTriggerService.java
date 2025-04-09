package com.cephalea.backend.service;


import com.cephalea.backend.dto.PotentialTriggerCrudDto;
import com.cephalea.backend.dto.PotentialTriggerDto;
import com.cephalea.backend.entity.PotentialTriggerEntity;
import com.cephalea.backend.mapper.PotentialTriggerDTOMapper;
import com.cephalea.backend.repository.PotentialTriggerRepository;
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
public class PotentialTriggerService {

    private final PotentialTriggerRepository potentialTriggerRepository;
    private final PotentialTriggerDTOMapper potentialTriggerDTOMapper;

    @Autowired
    public PotentialTriggerService(PotentialTriggerRepository potentialTriggerRepository, PotentialTriggerDTOMapper potentialTriggerDTOMapper) {
        this.potentialTriggerRepository = potentialTriggerRepository;
        this.potentialTriggerDTOMapper = potentialTriggerDTOMapper;
    }

    //Read all Potential Trigger
    @Transactional(readOnly = true)
    public List<PotentialTriggerDto> findAll() {
        log.debug("Find all PotentialTriggers");
        List<PotentialTriggerEntity> triggers = potentialTriggerRepository.findAll();
        List<PotentialTriggerDto> potentialTriggerdtos = triggers.stream().map(potentialTriggerDTOMapper::toDTO).toList();
        log.debug("FindAll- Found {} potential triggers", potentialTriggerdtos.size());
        log.debug("FindAll- got list {}", potentialTriggerdtos);
        return potentialTriggerdtos;
    }

    //Read One Potential Trigger
    @Transactional(readOnly = true)
    public PotentialTriggerDto findByUUID(UUID id) {
        log.debug("Find Trigger by UUID {}", id);
        return potentialTriggerRepository.findById(id)
                .map(potentialTriggerDTOMapper::toDTO)
                .map(potentialTriggerdto -> {
                    log.debug("Find trigger by UUID {}", potentialTriggerdto);
                    return potentialTriggerdto;
                })
                .orElseThrow(() -> new EntityNotFoundException("trigger not found with UUID: " + id));
    }

    //Create Potential Trigger
    public PotentialTriggerDto createTrigger(PotentialTriggerCrudDto potentialTriggerCrudDto) {
        log.debug("Create trigger {}", potentialTriggerCrudDto);
        if (potentialTriggerRepository.existsByName(potentialTriggerCrudDto.getName())) {
            log.debug("Trigger with name {} already exists", potentialTriggerCrudDto.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Trigger with name " + potentialTriggerCrudDto.getName() + " already exists.");
        }

        //Map DTO to entity
        PotentialTriggerEntity potentialtriggerEntity = potentialTriggerDTOMapper.toEntity(potentialTriggerCrudDto);


        //Save Trigger
        PotentialTriggerEntity savedTriggerEntity = potentialTriggerRepository.save(potentialtriggerEntity);
        log.debug("Create trigger {}", savedTriggerEntity);
        return potentialTriggerDTOMapper.toDTO(savedTriggerEntity);
    }

    //Update Trigger
    public PotentialTriggerDto updateTrigger(PotentialTriggerCrudDto potentialTriggerCrudDto, UUID id) {
        log.debug("Update Trigger {}", potentialTriggerCrudDto);

        //Find Existing by Name
        PotentialTriggerEntity triggerToUpdate = potentialTriggerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trigger not found with ID: " + id));


        if (!potentialTriggerCrudDto.getName().equals(triggerToUpdate.getName()) && potentialTriggerRepository.existsByName(potentialTriggerCrudDto.getName())) {
            log.error("Trigger with name {} already exists", potentialTriggerCrudDto.getName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trigger with name " + potentialTriggerCrudDto.getName() + " already exists.");
        }


        triggerToUpdate.setName(potentialTriggerCrudDto.getName());


        PotentialTriggerEntity updatedTriggerEntity = potentialTriggerRepository.save(triggerToUpdate);
        log.debug("Update Trigger {}", updatedTriggerEntity);
        return potentialTriggerDTOMapper.toDTO(updatedTriggerEntity);
    }

    //Delete Trigger
    public void deleteTrigger(UUID id) {
        log.debug("Delete trigger {}", id);
        if (!potentialTriggerRepository.existsById(id)) {
            throw new EntityNotFoundException("Trigger not found with ID: " + id);
        }
        potentialTriggerRepository.deleteById(id);
        log.debug("Delete trigger {}", id);
    }
}
