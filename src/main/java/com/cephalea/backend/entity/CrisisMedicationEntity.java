package com.cephalea.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "crisis_medication")
public class CrisisMedicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "crisis_id")
    private CrisisEntity crisis;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private MedicationEntity medication;

    @Column(name = "date_time_intake")
    private LocalDateTime dateTimeIntake;


}
