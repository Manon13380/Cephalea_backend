package com.cephalea.backend.repository;

import com.cephalea.backend.entity.PotentialTriggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PotentialTriggerRepository extends JpaRepository<PotentialTriggerEntity, UUID> {

    Boolean existsByName(String name);
}
