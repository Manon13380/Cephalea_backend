package com.cephalea.backend.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @id
    private String id;

    @NotBlank(message = "Le nom ne doit pas être vide")
    @Size(min=2)
    private String name;

    @NotBlank(message = "Le prénom ne doit pas être vide")
    @Size(min=2)
    private String firstName;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @NotBlank(message = "Le mot de passe ne doit pas être vide")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{8,}$",
    message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial")
    private String password;

    @NotBlank(message = "L'email ne doit pas être vide")
    @Email(message = "Email invalide")
    private String email;

    @Size(min=2)
    private String doctor;

    @Size(min=2)
    private String neurologist;
}
