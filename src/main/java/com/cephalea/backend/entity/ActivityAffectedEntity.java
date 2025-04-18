package com.cephalea.backend.entity;

import jakarta.annotation.PostConstruct;
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
@Table(name ="activity_affected")
public class ActivityAffectedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    @ToString.Include
    private String name;

    @ManyToMany(mappedBy = "activities")
    private Set<CrisisEntity> crisis = new HashSet<>();

}
