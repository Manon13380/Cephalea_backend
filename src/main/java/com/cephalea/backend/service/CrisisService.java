package com.cephalea.backend.service;

import com.cephalea.backend.dto.CrisisCrudDto;
import com.cephalea.backend.dto.CrisisDto;
import com.cephalea.backend.entity.CrisisEntity;
import com.cephalea.backend.mapper.CrisisDTOMapper;
import com.cephalea.backend.repository.CrisisRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class CrisisService {


    private final CrisisRepository crisisRepository;
    private final CrisisDTOMapper crisisDTOMapper;

    @Autowired
    public CrisisService(CrisisRepository crisisRepository, CrisisDTOMapper crisisDTOMapper) {
        this.crisisRepository = crisisRepository;
        this.crisisDTOMapper = crisisDTOMapper;
    }

    //Read all Crisis
    @Transactional(readOnly = true)
    public List<CrisisDto> findAll() {
        log.debug("Find all crisis");
        List<CrisisEntity> crisis = crisisRepository.findAll();
        List<CrisisDto> crisisDto = crisis.stream().map(crisisDTOMapper::toDTO).toList();
        log.debug("FindAll- Found {} crisis", crisisDto.size());
        log.debug("FindAll- got list {}", crisisDto);
        return crisisDto;
    }

    //Read One crisis
    @Transactional(readOnly = true)
    public CrisisDto findByUUID(UUID id) {
        log.debug("Find crisis by UUID {}", id);
        return crisisRepository.findById(id)
                .map(crisisDTOMapper::toDTO)
                .map(crisisDto -> {
                    log.debug("Find crisis by UUID {}", crisisDto);
                    return crisisDto;
                })
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found with UUID: " + id));

    }

    //Create Crisis
    public CrisisDto createCrisis(CrisisCrudDto crisisCrudDto) {
        log.debug("Create crisis {}", crisisCrudDto);
        //Map DTO to entity
        CrisisEntity crisisEntity = crisisDTOMapper.toEntity(crisisCrudDto);

        //Save crisis
        CrisisEntity savedCrisisEntity = crisisRepository.save(crisisEntity);
        log.debug("Create crisis {}", savedCrisisEntity);
        return crisisDTOMapper.toDTO(savedCrisisEntity);
    }

    //Update crisis
    public CrisisDto updateCrisis(CrisisCrudDto crisisCrudDto, UUID id) {
        log.debug("Update crisis {}", crisisCrudDto);

        CrisisEntity crisisToUpdate = crisisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found with ID: " + id));


        if (crisisCrudDto.getStartDate() != null)
            crisisToUpdate.setStartDate(crisisCrudDto.getStartDate());

        if (crisisCrudDto.getEndDate() != null)
            crisisToUpdate.setEndDate(crisisCrudDto.getEndDate());

        if (crisisCrudDto.getComment() != null)
            crisisToUpdate.setComment(crisisCrudDto.getComment());

        if (crisisCrudDto.getUser() != null)
            crisisToUpdate.setUser(crisisCrudDto.getUser());

        if (crisisCrudDto.getSoulagements() != null)
            crisisToUpdate.setSoulagements(crisisCrudDto.getSoulagements());

        if (crisisCrudDto.getActivities() != null)
            crisisToUpdate.setActivities(crisisCrudDto.getActivities());

        if (crisisCrudDto.getTriggers() != null)
            crisisToUpdate.setTriggers(crisisCrudDto.getTriggers());

        if (crisisCrudDto.getIntensities() != null)
            crisisToUpdate.setIntensities(crisisCrudDto.getIntensities());

        if (crisisCrudDto.getCrisisMedication() != null)
            crisisToUpdate.setCrisisMedication(crisisCrudDto.getCrisisMedication());


        CrisisEntity updatedCrisisEntity = crisisRepository.save(crisisToUpdate);
        log.debug("Update usercrisis {}", updatedCrisisEntity);
        return crisisDTOMapper.toDTO(updatedCrisisEntity);
    }

    //Delete Crisis
    public void deleteCrisis(UUID id) {
        log.debug("Delete crisis {}", id);
        if (!crisisRepository.existsById(id)) {
            throw new EntityNotFoundException("Crisis not found with ID: " + id);
        }
        crisisRepository.deleteById(id);
        log.debug("Delete crisis {}", id);
    }
}
