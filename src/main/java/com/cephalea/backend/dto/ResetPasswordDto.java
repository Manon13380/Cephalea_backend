package com.cephalea.backend.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResetPasswordDto {
    private String token;
    private String newPassword;
    private String confirmPassword;
}