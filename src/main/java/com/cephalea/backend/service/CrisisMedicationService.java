package com.cephalea.backend.service;


import com.cephalea.backend.dto.CrisisMedicationCrudDto;
import com.cephalea.backend.dto.CrisisMedicationDto;
import com.cephalea.backend.entity.CrisisEntity;
import com.cephalea.backend.entity.CrisisMedicationEntity;
import com.cephalea.backend.entity.MedicationEntity;
import com.cephalea.backend.mapper.CrisisMedicationDTOMapper;
import com.cephalea.backend.repository.CrisisMedicationRepository;
import com.cephalea.backend.repository.CrisisRepository;
import com.cephalea.backend.repository.MedicationRepository;
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
    private final CrisisRepository crisisRepository;
    private final MedicationRepository medicationRepository;

    @Autowired
    public CrisisMedicationService(CrisisMedicationRepository crisisMedicationRepository, CrisisMedicationDTOMapper crisisMedicationDTOMapper, CrisisRepository crisisRepository, MedicationRepository medicationRepository) {
        this.crisisMedicationRepository = crisisMedicationRepository;
        this.crisisMedicationDTOMapper = crisisMedicationDTOMapper;
        this.crisisRepository = crisisRepository;
        this.medicationRepository = medicationRepository;
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
    public CrisisMedicationDto createCrisisMedication(CrisisMedicationCrudDto crisisMedicationCrudDto, UUID id, UUID medicationId) {
        log.info("Create CrisisMedication {}", crisisMedicationCrudDto);
        CrisisEntity crisis = crisisRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found with ID: " + id));

        MedicationEntity medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found with ID: " + medicationId));

        if (crisisMedicationRepository.existsByCrisisAndMedicationAndDateTimeIntake(crisis, medication, crisisMedicationCrudDto.getDateTimeIntake())) {
            log.info("CrisisMedication with date {} already exists", crisisMedicationCrudDto.getDateTimeIntake());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CrisisMedication with date " + crisisMedicationCrudDto.getDateTimeIntake() + " already exists.");
        }
        //Map DTO to entity
        CrisisMedicationEntity crisisMedicationEntity = crisisMedicationDTOMapper.toEntity(crisisMedicationCrudDto);
        crisisMedicationEntity.setMedication(medication);
        crisisMedicationEntity.setCrisis(crisis);


        //Save Activity
        CrisisMedicationEntity savedCrisisMedicationEntity = crisisMedicationRepository.save(crisisMedicationEntity);
        log.info("Create CrisisMedication{}", savedCrisisMedicationEntity.getMedication());
        log.info(" Medication ID = {}", savedCrisisMedicationEntity.getMedication() != null ? savedCrisisMedicationEntity.getMedication().getId() : "null");
        return crisisMedicationDTOMapper.toDTO(savedCrisisMedicationEntity);
    }

    //Update CrisisMedication
    public CrisisMedicationDto updateCrisisMedication(CrisisMedicationCrudDto crisisMedicationCrudDto, UUID crisisId, UUID crisisMedicationId, UUID medicationId) {
        log.debug("Update crisisMedication {}", crisisMedicationCrudDto);

        CrisisMedicationEntity crisisMedicationToUpdate = crisisMedicationRepository.findById(crisisMedicationId)
                .orElseThrow(() -> new EntityNotFoundException("CrisisMedication not found with ID: " + crisisMedicationId));

        CrisisEntity crisis = crisisRepository.findById(crisisId)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found with ID: " + crisisId));

        MedicationEntity medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found with ID: " + medicationId));


        if (crisisMedicationRepository.existsByCrisisAndMedicationAndDateTimeIntake(crisis, medication, crisisMedicationCrudDto.getDateTimeIntake())) {
            log.debug("CrisisMedication with date {} already exists", crisisMedicationCrudDto.getDateTimeIntake());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CrisisMedication with date " + crisisMedicationCrudDto.getDateTimeIntake() + " already exists.");
        }

        if (medication != crisisMedicationToUpdate.getMedication())
            crisisMedicationToUpdate.setMedication(medication);

        if (crisisMedicationCrudDto.getDateTimeIntake() != null)
            crisisMedicationToUpdate.setDateTimeIntake(crisisMedicationCrudDto.getDateTimeIntake());


        CrisisMedicationEntity updatedCrisisMedicationEntity = crisisMedicationRepository.save(crisisMedicationToUpdate);
        log.debug("Update crisiMedication {}", updatedCrisisMedicationEntity);
        return crisisMedicationDTOMapper.toDTO(updatedCrisisMedicationEntity);
    }

    //Delete CrisisMedication
    public void deleteCrisisMedication(UUID id) {
        log.debug("Delete CrisisMedication {}", id);

        CrisisMedicationEntity crisisMedication = crisisMedicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("CrisisMedication not found with ID: " + id));

        MedicationEntity medication = crisisMedication.getMedication();

        crisisMedicationRepository.deleteById(id);
        log.debug("Delete activity {}", id);

        // Vérifie si la medication est à supprimer
        if (medication != null && Boolean.FALSE.equals(medication.getIsTreatment())) {

            medicationRepository.deleteById(medication.getId());
            log.debug("Deleted Medication {} because isTreatment was false and no longer used", medication.getId());

        }
    }
}
