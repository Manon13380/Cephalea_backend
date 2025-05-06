package com.cephalea.backend.dto;

import com.cephalea.backend.enumeration.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCrudDto {
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

    @NotBlank(message = "Le mot de passe ne doit pas être vide")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%&*-]).{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial")
    private String confirmPassword;

    @NotBlank(message = "L'email ne doit pas être vide")
    @Email(message = "Email invalide")
    private String email;

    @Size(min=2)
    private String doctor;

    @Size(min=2)
    private String neurologist;

    private Role role;


}
