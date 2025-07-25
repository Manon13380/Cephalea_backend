package com.cephalea.backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "intensity")
public class IntensityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "date", nullable = false)
    @ToString.Include
    private LocalDateTime date;

    @Column(name = "number", nullable = false)
    @ToString.Include
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "crisis_id", nullable = false)
    private CrisisEntity crisis;
}
