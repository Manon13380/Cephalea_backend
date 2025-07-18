package com.cephalea.backend.controller;

import com.cephalea.backend.dto.ResetPasswordDto;
import com.cephalea.backend.dto.UserCrudDto;
import com.cephalea.backend.dto.UserDto;
import com.cephalea.backend.entity.PasswordResetToken;
import com.cephalea.backend.security.JwtService;
import com.cephalea.backend.entity.UserEntity;
import com.cephalea.backend.repository.UserRepository;
import com.cephalea.backend.service.PasswordResetService;
import com.cephalea.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordResetService passwordResetService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, UserService userService, PasswordResetService passwordResetService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody UserEntity userEntity) {
        // Authentifier l'utilisateur via Spring Security
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userEntity.getEmail(), userEntity.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Si l'authentification est réussie, récupérer l'utilisateur
        UserEntity user = userRepository.findByEmail(userEntity.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);

        return token;  // Retourner le token JWT
    }
    @PostMapping("/auth/signup")
    public ResponseEntity<UserDto> UserPost(@Valid @RequestBody UserCrudDto userDTO) {
        log.debug("UserPost {}", userDTO);
        UserDto createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/auth/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        passwordResetService.createPasswordResetToken(email);
        return ResponseEntity.ok("Un lien de réinitialisation a été envoyé à votre adresse e-mail.");
    }

    @PostMapping("/auth/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto request) {
        boolean success = passwordResetService.resetPassword(request.getToken(), request.getNewPassword(), request.getConfirmPassword());
        return success ? ResponseEntity.ok("Mot de passe mis à jour.") :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lien invalide ou expiré.");

}}
