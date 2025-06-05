package com.cephalea.backend.service;

import com.cephalea.backend.dto.CrisisCrudDto;
import com.cephalea.backend.dto.CrisisDto;
import com.cephalea.backend.entity.CrisisEntity;
import com.cephalea.backend.entity.IntensityEntity;
import com.cephalea.backend.entity.UserEntity;
import com.cephalea.backend.mapper.CrisisDTOMapper;
import com.cephalea.backend.repository.CrisisRepository;
import com.cephalea.backend.repository.IntensityRepository;
import com.cephalea.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class CrisisService {


    private final CrisisRepository crisisRepository;
    private final CrisisDTOMapper crisisDTOMapper;

    private final IntensityRepository intensityRepository;
    private final UserRepository userRepository;


    @Autowired
    public CrisisService(CrisisRepository crisisRepository, CrisisDTOMapper crisisDTOMapper, IntensityRepository intensityRepository, UserRepository userRepository) {
        this.crisisRepository = crisisRepository;
        this.crisisDTOMapper = crisisDTOMapper;
        this.intensityRepository = intensityRepository;
        this.userRepository = userRepository;
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
