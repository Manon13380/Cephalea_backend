package com.cephalea.backend.repository;

import com.cephalea.backend.entity.PasswordResetToken;
import com.cephalea.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByToken(String token);

    void deleteAllByUser(UserEntity user);
}