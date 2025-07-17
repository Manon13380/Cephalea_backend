package com.cephalea.backend.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordUpdateDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
