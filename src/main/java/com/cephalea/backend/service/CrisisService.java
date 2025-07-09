package com.cephalea.backend.service;

import com.cephalea.backend.dto.*;
import com.cephalea.backend.entity.*;
import com.cephalea.backend.mapper.ActivityAffectedDTOMapper;
import com.cephalea.backend.mapper.CrisisDTOMapper;
import com.cephalea.backend.mapper.PotentialTriggerDTOMapper;
import com.cephalea.backend.mapper.SoulagementDTOMapper;
import com.cephalea.backend.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class CrisisService {


    private final CrisisRepository crisisRepository;
    private final CrisisDTOMapper crisisDTOMapper;
    private final SoulagementRepository soulagementRepository;
    private final UserRepository userRepository;
    private final SoulagementDTOMapper soulagementDTOMapper;
    private final PotentialTriggerRepository potentialTriggerRepository;
    private final PotentialTriggerDTOMapper potentialTriggerDTOMapper;
    private final ActivityAffectedRepository activityAffectedRepository;
    private final ActivityAffectedDTOMapper activityAffectedDTOMapper;


    @Autowired
    public CrisisService(CrisisRepository crisisRepository, CrisisDTOMapper crisisDTOMapper, UserRepository userRepository, SoulagementRepository soulagementRepository, SoulagementDTOMapper soulagementDTOMapper, PotentialTriggerRepository potentialTriggerRepository, PotentialTriggerDTOMapper potentialTriggerDTOMapper, ActivityAffectedRepository activityAffectedRepository, ActivityAffectedDTOMapper activityAffectedDTOMapper) {
        this.crisisRepository = crisisRepository;
        this.crisisDTOMapper = crisisDTOMapper;
        this.soulagementRepository = soulagementRepository;
        this.userRepository = userRepository;
        this.soulagementDTOMapper = soulagementDTOMapper;
        this.potentialTriggerRepository = potentialTriggerRepository;
        this.potentialTriggerDTOMapper = potentialTriggerDTOMapper;
        this.activityAffectedRepository = activityAffectedRepository;
        this.activityAffectedDTOMapper = activityAffectedDTOMapper;
    }

    //Read all Crisis by user email
    @Transactional(readOnly = true)
    public List<CrisisDto> findAllByUserEmail(String email) {
        log.debug("Find all crisis");
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        List<CrisisEntity> crisis = crisisRepository.findAllByUser_Id(user.getId());
        List<CrisisDto> crisisDto = crisis.stream().map(crisisDTOMapper::toDTO).toList();
        log.debug("FindAll- Found {} crisis", crisisDto.size());
        log.debug("FindAll- got list {}", crisisDto);
        return crisisDto;
    }

    //Read One crisis
    @Transactional(readOnly = true)
    public CrisisDto findByUUID(UUID id) {
        log.debug("Find crisis by UUID {}", id);
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userEmail));

        CrisisEntity crisis = crisisRepository.findByIdAndUser_Id(id, user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found or not authorized for this user"));

        return crisisDTOMapper.toDTO(crisis);

    }

    //Create Crisis
    public CrisisDto createCrisis(CrisisCrudDto crisisCrudDto , int painIntensity, String userName) {
        log.debug("Create crisis {}", crisisCrudDto);
        //Map DTO to entity
        CrisisEntity crisisEntity = crisisDTOMapper.toEntity(crisisCrudDto);
        UserEntity user = userRepository.findByEmail(userName)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found with email " + userName));
        crisisEntity.setUser(user);
        //Save crisis
        CrisisEntity savedCrisisEntity = crisisRepository.save(crisisEntity);

        IntensityEntity intensity = new IntensityEntity();
        intensity.setDate(LocalDateTime.now());
        intensity.setNumber(painIntensity);
        intensity.setCrisis(savedCrisisEntity);

        savedCrisisEntity.getIntensities().add(intensity);


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

        if (crisisCrudDto.getSoulagements() != null  && !crisisCrudDto.getSoulagements().isEmpty())
            crisisToUpdate.setSoulagements(crisisCrudDto.getSoulagements());

        if (crisisCrudDto.getActivities() != null && !crisisCrudDto.getActivities().isEmpty())
            crisisToUpdate.setActivities(crisisCrudDto.getActivities());

        if (crisisCrudDto.getTriggers() != null && !crisisCrudDto.getTriggers().isEmpty())
            crisisToUpdate.setTriggers(crisisCrudDto.getTriggers());

        if (crisisCrudDto.getIntensities() != null && !crisisCrudDto.getIntensities().isEmpty())
            crisisToUpdate.setIntensities(crisisCrudDto.getIntensities());

        if (crisisCrudDto.getCrisisMedication() != null && !crisisCrudDto.getCrisisMedication().isEmpty())
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

    // add relief
    public SoulagementDto addSoulagement(UUID crisisId, UUID soulagementId) {
        CrisisEntity crisis = crisisRepository.findById(crisisId)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found"));

        SoulagementEntity soulagement = soulagementRepository.findById(soulagementId)
                .orElseThrow(() -> new EntityNotFoundException("Soulagement not found"));

        crisis.getSoulagements().add(soulagement);

        crisisRepository.save(crisis);
        return soulagementDTOMapper.toDTO(soulagement);
    }

    public void removeSoulagement(UUID crisisId, UUID soulagementId) {
        CrisisEntity crisis = crisisRepository.findById(crisisId)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found"));

        SoulagementEntity soulagement = soulagementRepository.findById(soulagementId)
                .orElseThrow(() -> new EntityNotFoundException("Soulagement not found"));

        if (!crisis.getSoulagements().remove(soulagement)) {
            throw new IllegalStateException("This soulagement is not linked to the crisis");
        }

        crisisRepository.save(crisis);
    }
    public PotentialTriggerDto addTrigger(UUID crisisId, UUID triggerId) {
        CrisisEntity crisis = crisisRepository.findById(crisisId)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found"));

        PotentialTriggerEntity trigger = potentialTriggerRepository.findById(triggerId)
                .orElseThrow(() -> new EntityNotFoundException("Trigger not found"));

        crisis.getTriggers().add(trigger);
        crisisRepository.save(crisis);

        return potentialTriggerDTOMapper.toDTO(trigger);
    }

    public void removeTrigger(UUID crisisId, UUID triggerId) {
        CrisisEntity crisis = crisisRepository.findById(crisisId)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found"));

        PotentialTriggerEntity trigger = potentialTriggerRepository.findById(triggerId)
                .orElseThrow(() -> new EntityNotFoundException("Trigger not found"));

        if (!crisis.getTriggers().remove(trigger)) {
            throw new IllegalStateException("This trigger is not linked to the crisis");
        }

        crisisRepository.save(crisis);
    }
    public ActivityAffectedDto addActivity(UUID crisisId, UUID activityId) {
        CrisisEntity crisis = crisisRepository.findById(crisisId)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found"));

        ActivityAffectedEntity activity = activityAffectedRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        crisis.getActivities().add(activity);
        crisisRepository.save(crisis);

        return activityAffectedDTOMapper.toDTO(activity);
    }

    public void removeActivity(UUID crisisId, UUID activityId) {
        CrisisEntity crisis = crisisRepository.findById(crisisId)
                .orElseThrow(() -> new EntityNotFoundException("Crisis not found"));

        ActivityAffectedEntity activity = activityAffectedRepository.findById(activityId)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));

        if (!crisis.getActivities().remove(activity)) {
            throw new IllegalStateException("This activity is not linked to the crisis");
        }

        crisisRepository.save(crisis);
    }


}
