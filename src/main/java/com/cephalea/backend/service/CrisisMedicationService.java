package com.cephalea.backend.service;



import com.cephalea.backend.dto.CrisisMedicationCrudDto;
import com.cephalea.backend.dto.CrisisMedicationDto;
import com.cephalea.backend.entity.CrisisMedicationEntity;
import com.cephalea.backend.mapper.CrisisMedicationDTOMapper;
import com.cephalea.backend.repository.CrisisMedicationRepository;
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
public class CrisisMedicationService {

    private final CrisisMedicationRepository crisisMedicationRepository;
    private final CrisisMedicationDTOMapper crisisMedicationDTOMapper;

    @Autowired
    public CrisisMedicationService(CrisisMedicationRepository crisisMedicationRepository, CrisisMedicationDTOMapper crisisMedicationDTOMapper) {
        this.crisisMedicationRepository = crisisMedicationRepository;
        this.crisisMedicationDTOMapper = crisisMedicationDTOMapper;
    }

    //Read all CrisisMedications
    @Transactional(readOnly = true)
    public List<CrisisMedicationDto> findAll() {
        log.debug("Find all CrisisMedication Affected");
        List<CrisisMedicationEntity> crisisMedications = crisisMedicationRepository.findAll();
        List<CrisisMedicationDto> crisisMedicationsDto = crisisMedications.stream().map(crisisMedicationDTOMapper::toDTO).toList();
        log.debug("FindAll- Found {} CrisisMedicationDto", crisisMedicationsDto.size());
        log.debug("FindAll- got list {}", crisisMedicationsDto);
        return crisisMedicationsDto;
    }

    //Read One CrisisMedications
    @Transactional(readOnly = true)
    public CrisisMedicationDto findByUUID(UUID id) {
        log.debug("Find CrisisMedication by UUID {}", id);
        return crisisMedicationRepository.findById(id)
                .map(crisisMedicationDTOMapper::toDTO)
                .map(crisisMedicationDto -> {
                    log.debug("Find CrisisMedications by UUID {}", crisisMedicationDto);
                    return crisisMedicationDto;
                })
                .orElseThrow(() -> new EntityNotFoundException("CrisisMedications not found with UUID: " + id));
    }

    //Create CrisisMedication
    public CrisisMedicationDto createCrisisMedication(CrisisMedicationCrudDto crisisMedicationCrudDto) {
        log.debug("Create CrisisMedication {}", crisisMedicationCrudDto);

        if (crisisMedicationRepository.existsByCrisisAndMedicationAndDateTimeIntake(crisisMedicationCrudDto.getCrisis(), crisisMedicationCrudDto.getMedication(), crisisMedicationCrudDto.getDateTimeIntake()))
        {
            log.debug("CrisisMedication with date {} already exists", crisisMedicationCrudDto.getDateTimeIntake());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CrisisMedication with date " + crisisMedicationCrudDto.getDateTimeIntake() + " already exists.");
        }
        //Map DTO to entity
        CrisisMedicationEntity crisisMedicationEntity = crisisMedicationDTOMapper.toEntity(crisisMedicationCrudDto);


        //Save Activity
        CrisisMedicationEntity savedCrisisMedicationEntity = crisisMedicationRepository.save(crisisMedicationEntity);
        log.debug("Create activity {}", savedCrisisMedicationEntity);
        return crisisMedicationDTOMapper.toDTO(savedCrisisMedicationEntity);
    }

    //Update CrisisMedication
    public CrisisMedicationDto updateCrisisMedication(CrisisMedicationCrudDto crisisMedicationCrudDto, UUID id) {
        log.debug("Update crisisMedication {}", crisisMedicationCrudDto);


        CrisisMedicationEntity crisisMedicationToUpdate = crisisMedicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found with ID: " + id));


        if (crisisMedicationRepository.existsByCrisisAndMedicationAndDateTimeIntake(crisisMedicationCrudDto.getCrisis(), crisisMedicationCrudDto.getMedication(), crisisMedicationCrudDto.getDateTimeIntake()))
        {
            log.debug("CrisisMedication with date {} already exists", crisisMedicationCrudDto.getDateTimeIntake());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CrisisMedication with date " + crisisMedicationCrudDto.getDateTimeIntake() + " already exists.");
        }

        if (crisisMedicationCrudDto.getMedication() != null)
            crisisMedicationToUpdate.setMedication(crisisMedicationCrudDto.getMedication());

        if (crisisMedicationCrudDto.getDateTimeIntake() != null)
            crisisMedicationToUpdate.setDateTimeIntake(crisisMedicationCrudDto.getDateTimeIntake());


        CrisisMedicationEntity updatedCrisisMedicationEntity = crisisMedicationRepository.save(crisisMedicationToUpdate);
        log.debug("Update crisiMedication {}", updatedCrisisMedicationEntity);
        return crisisMedicationDTOMapper.toDTO(updatedCrisisMedicationEntity);
    }

    //Delete CrisisMedication
    public void deleteCrisisMedication(UUID id) {
        log.debug("Delete CrisisMedication {}", id);
        if (!crisisMedicationRepository.existsById(id)) {
            throw new EntityNotFoundException("CrisisMedication not found with ID: " + id);
        }
        crisisMedicationRepository.deleteById(id);
        log.debug("Delete activity {}", id);
    }
}
