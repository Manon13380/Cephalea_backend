package com.cephalea.backend.repository;

import com.cephalea.backend.entity.CrisisEntity;
import com.cephalea.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CrisisRepository extends JpaRepository<CrisisEntity, UUID> {
    @Query("SELECT DISTINCT c FROM CrisisEntity c " +
            "LEFT JOIN FETCH c.crisisMedication cm " +
            "LEFT JOIN FETCH cm.medication " +
            "WHERE c.user.id = :userId")
    List<CrisisEntity> findAllWithMedicationsByUserId(@Param("userId") UUID userId);
    List<CrisisEntity> findAllByUser_Id(UUID userId);

    Optional<CrisisEntity> findByIdAndUser_Id(UUID id, UUID userId);
}
