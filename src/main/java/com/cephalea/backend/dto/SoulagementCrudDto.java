package com.cephalea.backend.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SoulagementCrudDto {
    @NotBlank(message = "Le nom ne doit pas être vide")
    private String name;

}
