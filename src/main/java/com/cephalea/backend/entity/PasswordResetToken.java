package com.cephalea.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name ="PasswordResetToken")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "token", nullable = false)
    private String token;

    @OneToOne
    private UserEntity user;

    @Column(name = "expirationDate", nullable = false)
    private LocalDateTime expirationDate;
}
