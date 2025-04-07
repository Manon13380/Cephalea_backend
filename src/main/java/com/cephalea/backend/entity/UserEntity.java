package com.cephalea.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.time.LocalDate;
import java.util.UUID;


@Setter
@Getter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "app_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    @ToString.Include
    private String name;

    @Column(name = "first_name", nullable = false)
    @ToString.Include
    private String firstName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "doctor")
    private String doctor;

    @Column(name = "neurologist")
    private String neurologist;


}
