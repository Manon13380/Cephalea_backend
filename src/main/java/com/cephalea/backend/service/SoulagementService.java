package com.cephalea.backend.service;

import com.cephalea.backend.dto.SoulagementCrudDto;
import com.cephalea.backend.dto.SoulagementDto;
import com.cephalea.backend.dto.UserCrudDto;
import com.cephalea.backend.dto.UserDto;
import com.cephalea.backend.entity.SoulagementEntity;
import com.cephalea.backend.entity.UserEntity;
import com.cephalea.backend.mapper.SoulagementDTOMapper;
import com.cephalea.backend.repository.SoulagementRepository;
import com.cephalea.backend.security.PasswordHasher;
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
public class SoulagementService {

    private final SoulagementRepository soulagementRepository;
    private final SoulagementDTOMapper soulagementDTOMapper;

    @Autowired
    public SoulagementService(SoulagementRepository soulagementRepository, SoulagementDTOMapper soulagementDTOMapper) {
        this.soulagementRepository = soulagementRepository;
        this.soulagementDTOMapper = soulagementDTOMapper;
    }

    //Read all Soulagement
    @Transactional(readOnly = true)
    public List<SoulagementDto> findAll() {
        log.debug("Find all soulagements");
        List<SoulagementEntity> soulagements = soulagementRepository.findAll();
        List<SoulagementDto> soulagementsDto = soulagements.stream().map(soulagementDTOMapper::toDTO).toList();
        log.debug("FindAll- Found {} soulagements", soulagementsDto.size());
        log.debug("FindAll- got list {}", soulagementsDto);
        return soulagementsDto;
    }

    //Read One Soulagement
    @Transactional(readOnly = true)
    public SoulagementDto findByUUID(UUID id) {
        log.debug("Find soulagement by UUID {}", id);
        return soulagementRepository.findById(id)
                .map(soulagementDTOMapper::toDTO)
                .map(soulagementDto -> {
                    log.debug("Find soulagement by UUID {}", soulagementDto);
                    return soulagementDto;
                })
                .orElseThrow(() -> new EntityNotFoundException("soulagement not found with UUID: " + id));
    }

    //Create Soulagement
    public SoulagementDto createSoulagement(SoulagementCrudDto soulagementCrudDto) {
        log.debug("Create soulagement {}", soulagementCrudDto);
        if (soulagementRepository.existsByName(soulagementCrudDto.getName())) {
            log.debug("Soulagement with name {} already exists", soulagementCrudDto.getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Soulagement with name " + soulagementCrudDto.getName() + " already exists.");
        }

        //Map DTO to entity
        SoulagementEntity soulagementEntity = soulagementDTOMapper.toEntity(soulagementCrudDto);


        //Save Soulagement
        SoulagementEntity savedSoulagementEntity = soulagementRepository.save(soulagementEntity);
        log.debug("Create soulagement {}", savedSoulagementEntity);
        return soulagementDTOMapper.toDTO(savedSoulagementEntity);
    }

    //Update Soulagement
    public SoulagementDto updateSoulagement(SoulagementCrudDto soulagementCrudDto, UUID id) {
        log.debug("Update soulagement {}", soulagementCrudDto);

        //Find Existing by Name
        SoulagementEntity soulagementToUpdate = soulagementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Soulagement not found with ID: " + id));


        if (!soulagementCrudDto.getName().equals(soulagementToUpdate.getName()) && soulagementRepository.existsByName(soulagementCrudDto.getName())) {
            log.error("soulagement with name {} already exists", soulagementCrudDto.getName());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Soulagement with name " + soulagementCrudDto.getName() + " already exists.");
        }


        soulagementToUpdate.setName(soulagementCrudDto.getName());


        SoulagementEntity updatedSoulagementEntity = soulagementRepository.save(soulagementToUpdate);
        log.debug("Update soulagement {}", updatedSoulagementEntity);
        return soulagementDTOMapper.toDTO(updatedSoulagementEntity);
    }

    //Delete soulagement
    public void deleteSoulagement(UUID id) {
        log.debug("Delete soulagement {}", id);
        if (!soulagementRepository.existsById(id)) {
            throw new EntityNotFoundException("Soulagement not found with ID: " + id);
        }
        soulagementRepository.deleteById(id);
        log.debug("Delete soulagement {}", id);
    }
}
