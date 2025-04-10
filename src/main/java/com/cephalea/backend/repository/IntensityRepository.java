package com.cephalea.backend.repository;

import com.cephalea.backend.entity.IntensityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IntensityRepository extends JpaRepository<IntensityEntity, UUID> {
}
