package com.cephalea.backend.dto;

import com.cephalea.backend.enumeration.Role;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String name;
    private String firstName;
    private LocalDate birthDate;
    private String password;
    private String email;
    private String doctor;
    private String neurologist;
    private Role role;


}
