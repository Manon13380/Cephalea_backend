package com.cephalea.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name ="potential_trigger")
public class PotentialTriggerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    @ToString.Include
    private String name;

    @ManyToMany(mappedBy = "triggers")
    private Set<CrisisEntity> crisis = new HashSet<>();
}
