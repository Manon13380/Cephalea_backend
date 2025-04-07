package com.cephalea.backend.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHasher {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Méthode pour hasher un mot de passe
    public static String hashPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    // Méthode pour vérifier le mot de passe (lors de la connexion)
    public static boolean checkPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }
}
