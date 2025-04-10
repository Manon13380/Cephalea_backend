package com.cephalea.backend.repository;

import com.cephalea.backend.entity.ActivityAffectedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ActivityAffectedRepository extends JpaRepository<ActivityAffectedEntity, UUID> {

    Boolean existsByName(String name);

}
