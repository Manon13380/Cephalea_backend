package com.cephalea.backend.repository;

import com.cephalea.backend.entity.CrisisEntity;
import com.cephalea.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CrisisRepository extends JpaRepository<CrisisEntity, UUID> {
    List<CrisisEntity> findAllByUser_Id(UUID userId);
}
