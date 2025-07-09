package com.cephalea.backend.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "crisis")
public class CrisisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name="comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "crisis_soulagement",
            joinColumns = @JoinColumn(name = "crisis_id"),
            inverseJoinColumns = @JoinColumn(name = "soulagement_id")
    )
    private Set<SoulagementEntity> soulagements = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "crisis_activity",
            joinColumns = @JoinColumn(name = "crisis_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<ActivityAffectedEntity> activities = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "crisis_trigger",
            joinColumns = @JoinColumn(name = "crisis_id"),
            inverseJoinColumns = @JoinColumn(name = "trigger_id")
    )
    private Set<PotentialTriggerEntity> triggers = new HashSet<>();

    @OneToMany(mappedBy = "crisis", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IntensityEntity> intensities = new HashSet<>();


    @OneToMany(mappedBy = "crisis")
    private Set<CrisisMedicationEntity> crisisMedication = new HashSet<>();

}
