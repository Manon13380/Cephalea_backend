package com.cephalea.backend.entity;

import com.cephalea.backend.enumeration.Period;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "medication")
public class MedicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    @ToString.Include
    private String name;

    @Column(name = "dosage")
    private String dosage;

    @Column(name="quantity")
    private Integer quantity;

    @Column(name = "period_quantity")
    @Enumerated(EnumType.STRING)
    private Period periodQuantity;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "period_duration")
    @Enumerated(EnumType.STRING)
    private Period periodDuration;

    @Column(name= "interval")
    private String interval;

    @Column(name ="maximum")
    private Integer maximum;

    @Column(name = "period_maximum")
    @Enumerated(EnumType.STRING)
    private Period periodMaximum;

    @Column(name = "is_delete")
    private Boolean isDelete = false;

    @Column(name = "is_alarm")
    private Boolean isAlarm;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "medication")
    private Set<CrisisMedicationEntity> crisisMedication = new HashSet<>();


}
