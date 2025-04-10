package com.cephalea.backend.service;


import com.cephalea.backend.dto.MedicationCrudDto;
import com.cephalea.backend.dto.MedicationDto;
import com.cephalea.backend.entity.MedicationEntity;
import com.cephalea.backend.mapper.MedicationDTOMapper;
import com.cephalea.backend.repository.MedicationRepository;
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
public class MedicationService {


    private final MedicationRepository medicationRepository;
    private final MedicationDTOMapper medicationDTOMapper;

    @Autowired
    public MedicationService(MedicationRepository medicationRepository, MedicationDTOMapper medicationDTOMapper) {
        this.medicationRepository = medicationRepository;
        this.medicationDTOMapper = medicationDTOMapper;
    }

    //Read all Medications
    @Transactional(readOnly = true)
    public List<MedicationDto> findAll() {
        log.debug("Find all medications");
        List<MedicationEntity> medications = medicationRepository.findAll();
        List<MedicationDto> medicationsDto = medications.stream().map(medicationDTOMapper::toDTO).toList();
        log.debug("FindAll- Found {} medications", medicationsDto.size());
        log.debug("FindAll- got list {}", medicationsDto);
        return medicationsDto;
    }

    //Read One medication
    @Transactional(readOnly = true)
    public MedicationDto findByUUID(UUID id) {
        log.debug("Find medication by UUID {}", id);
        return medicationRepository.findById(id)
                .map(medicationDTOMapper::toDTO)
                .map(medicationDto -> {
                    log.debug("Find medication by UUID {}", medicationDto);
                    return medicationDto;
                })
                .orElseThrow(() -> new EntityNotFoundException("Medication not found with UUID: " + id));

    }

    //Create Medication
    public MedicationDto createMedication(MedicationCrudDto medicationCrudDto) {
        log.debug("Create medication {}", medicationCrudDto);
        //Map DTO to entity
        MedicationEntity medicationEntity = medicationDTOMapper.toEntity(medicationCrudDto);

        //Save medication
        MedicationEntity savedMedicationEntity = medicationRepository.save(medicationEntity);
        log.debug("Create medication {}", savedMedicationEntity);
        return medicationDTOMapper.toDTO(savedMedicationEntity);
    }

    //Update Medication
    public MedicationDto updateMedication(MedicationCrudDto medicationCrudDto, UUID id) {
        log.debug("Update medication {}", medicationCrudDto);

        MedicationEntity medicationToUpdate = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medication not found with ID: " + id));

        if (medicationCrudDto.getName() != null)
            medicationToUpdate.setName(medicationCrudDto.getName());

        if (medicationCrudDto.getDosage() != null)
            medicationToUpdate.setDosage(medicationCrudDto.getDosage());

        if (medicationCrudDto.getQuantity() != null)
            medicationToUpdate.setQuantity(medicationCrudDto.getQuantity());

        if (medicationCrudDto.getPeriodQuantity() != null)
            medicationToUpdate.setPeriodQuantity(medicationCrudDto.getPeriodQuantity());

        if (medicationCrudDto.getDuration() != null)
            medicationToUpdate.setDuration(medicationCrudDto.getDuration());

        if (medicationCrudDto.getPeriodDuration() != null)
            medicationToUpdate.setPeriodDuration(medicationCrudDto.getPeriodDuration());

        if (medicationCrudDto.getInterval() != null)
            medicationToUpdate.setInterval(medicationCrudDto.getInterval());

        if (medicationCrudDto.getMaximum() != null)
            medicationToUpdate.setMaximum(medicationCrudDto.getMaximum());

        if (medicationCrudDto.getPeriodMaximum() != null)
            medicationToUpdate.setPeriodMaximum(medicationCrudDto.getPeriodMaximum());

        if (medicationCrudDto.getIsAlarm() != null)
            medicationToUpdate.setIsAlarm(medicationCrudDto.getIsAlarm());

        MedicationEntity updatedMedicationEntity = medicationRepository.save(medicationToUpdate);
        log.debug("Update medication {}", updatedMedicationEntity);
        return medicationDTOMapper.toDTO(updatedMedicationEntity);
    }

}
