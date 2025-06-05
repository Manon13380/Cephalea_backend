package com.cephalea.backend.service;


import com.cephalea.backend.dto.IntensityCrudDto;
import com.cephalea.backend.dto.IntensityDto;
import com.cephalea.backend.entity.CrisisEntity;
import com.cephalea.backend.entity.IntensityEntity;
import com.cephalea.backend.mapper.IntensityDTOMapper;
import com.cephalea.backend.repository.CrisisRepository;
import com.cephalea.backend.repository.IntensityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class IntensityService {

    private final IntensityRepository intensityRepository;
    private final IntensityDTOMapper intensityDTOMapper;
    private final CrisisRepository  crisisRepository;

    @Autowired
    public IntensityService(IntensityRepository intensityRepository, IntensityDTOMapper intensityDTOMapper, CrisisRepository crisisRepository) {
        this.intensityRepository = intensityRepository;
        this.intensityDTOMapper = intensityDTOMapper;
        this.crisisRepository = crisisRepository;
    }

    //Read all Intensities
    @Transactional(readOnly = true)
    public List<IntensityDto> findAll() {
        log.debug("Find all intensities");
        List<IntensityEntity> intensities = intensityRepository.findAll();
        List<IntensityDto> intensitiesDto = intensities.stream().map(intensityDTOMapper::toDTO).toList();
        log.debug("FindAll- Found {} intensities", intensitiesDto.size());
        log.debug("FindAll- got list {}", intensitiesDto);
        return intensitiesDto;
    }

    //Read One intensity
    @Transactional(readOnly = true)
    public IntensityDto findByUUID(UUID id) {
        log.debug("Find intensity by UUID {}", id);
        return intensityRepository.findById(id)
                .map(intensityDTOMapper::toDTO)
                .map(intensityDto -> {
                    log.debug("Find intensity by UUID {}", intensityDto);
                    return intensityDto;
                })
                .orElseThrow(() -> new EntityNotFoundException("Intensity not found with UUID: " + id));

    }

    //Create Intensity
    public IntensityDto createIntensity(IntensityCrudDto intensityCrudDto, UUID id) {
        log.debug("Create intensity {}", intensityCrudDto);
        //Map DTO to entity
        IntensityEntity intensityEntity = intensityDTOMapper.toEntity(intensityCrudDto);

        CrisisEntity crisis = crisisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found with id " + id));

        intensityEntity.setCrisis(crisis);
        //Save intensity
        IntensityEntity savedIntensityEntity = intensityRepository.save(intensityEntity);
        log.debug("Create intensity {}", savedIntensityEntity);
        return intensityDTOMapper.toDTO(savedIntensityEntity);
    }

    //Update intensity
    public IntensityDto updateIntensity(IntensityCrudDto intensityCrudDto, UUID id) {
        log.debug("Update intensity {}", intensityCrudDto);

        IntensityEntity intensityToUpdate = intensityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Intensity not found with ID: " + id));


        if (intensityCrudDto.getDate() != null)
            intensityToUpdate.setDate(intensityCrudDto.getDate());

        if (intensityCrudDto.getNumber() != null)
            intensityToUpdate.setNumber(intensityCrudDto.getNumber());



        IntensityEntity updatedIntensityEntity = intensityRepository.save(intensityToUpdate);
        log.debug("Update intensity {}", updatedIntensityEntity);
        return intensityDTOMapper.toDTO(updatedIntensityEntity);
    }

    //Delete Intensity
    public void deleteIntensity(UUID id) {
        log.debug("Delete Intensity {}", id);
        if (!intensityRepository.existsById(id)) {
            throw new EntityNotFoundException("Intensity not found with ID: " + id);
        }
        intensityRepository.deleteById(id);
        log.debug("Delete intensity {}", id);
    }
}
