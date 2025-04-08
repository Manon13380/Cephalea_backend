package com.cephalea.backend.repository;

import com.cephalea.backend.entity.SoulagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SoulagementRepository extends JpaRepository<SoulagementEntity, UUID> {

    Boolean existsByName(String name);
}
