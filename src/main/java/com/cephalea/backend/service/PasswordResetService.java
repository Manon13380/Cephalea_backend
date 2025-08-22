package com.cephalea.backend.service;

import com.cephalea.backend.entity.PasswordResetToken;
import com.cephalea.backend.entity.UserEntity;
import com.cephalea.backend.repository.PasswordResetTokenRepository;
import com.cephalea.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Value("${url.reset.pwd}")
    private String urlResetPwd;

    public void createPasswordResetToken(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return;

        UserEntity user = optionalUser.get();
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpirationDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);

        String resetUrl = "http://57.128.84.212:9090/reset-password?token=" + token;

        String subject = "Réinitialisation de mot de passe";

        String htmlContent = """
                    <html>
                    <head>
                        <style>
                            .container {
                                max-width: 600px;
                                margin: auto;
                                padding: 20px;
                                font-family: Arial, sans-serif;
                                border: 1px solid #eee;
                                border-radius: 10px;
                                background-color: #f9f9f9;
                                color: #333;
                            }
                            .button {
                                display: inline-block;
                                padding: 10px 20px;
                                margin-top: 20px;
                                font-size: 16px;
                                background-color: #007bff;
                                color: white;
                                text-decoration: none;
                                border-radius: 5px;
                            }
                            .footer {
                                margin-top: 30px;
                                font-size: 12px;
                                color: #777;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <h2>Réinitialisation de votre mot de passe</h2>
                            <p>Vous avez demandé à réinitialiser votre mot de passe.</p>
                            <p>Pour procéder, cliquez sur le bouton ci-dessous :</p>
                            <a href="%s" class="button">Réinitialiser mon mot de passe</a>
                            <p>Si vous n'avez pas demandé cette réinitialisation, ignorez simplement ce message.</p>
                            <div class="footer">
                                <p>&copy; 2025 Cephalea. Tous droits réservés.</p>
                            </div>
                        </div>
                    </body>
                    </html>
                """.formatted(resetUrl);
        emailService.sendEmail(email, subject, htmlContent);
    }

    public boolean resetPassword(String token, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return false; // ou tu peux lever une exception custom si tu veux différencier l'erreur
        }
        Optional<PasswordResetToken> optional = tokenRepository.findByToken(token);
        if (optional.isEmpty()) return false;

        PasswordResetToken resetToken = optional.get();

        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(resetToken);
            return false;
        }

        UserEntity user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);
        user.setResetToken(null);
        tokenRepository.delete(resetToken);

        return true;
    }
}
