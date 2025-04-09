package com.cephalea.backend.repository;

import com.cephalea.backend.entity.PotentialtriggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PotentialTriggerRepository extends JpaRepository<PotentialtriggerEntity, UUID> {

    Boolean existsByName(String name);
}
