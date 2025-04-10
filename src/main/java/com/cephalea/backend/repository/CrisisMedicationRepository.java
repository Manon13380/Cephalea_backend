package com.cephalea.backend.repository;

import com.cephalea.backend.entity.CrisisEntity;
import com.cephalea.backend.entity.CrisisMedicationEntity;
import com.cephalea.backend.entity.MedicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface CrisisMedicationRepository extends JpaRepository<CrisisMedicationEntity, UUID> {

    boolean existsByCrisisAndMedicationAndDate(CrisisEntity crisis, MedicationEntity medication, LocalDateTime date);

}
