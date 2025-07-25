package com.cephalea.backend.controller;

import com.cephalea.backend.dto.PasswordUpdateDto;
import com.cephalea.backend.dto.UserCrudDto;
import com.cephalea.backend.dto.UserDto;
import com.cephalea.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UserDto>> usersGet() {
        log.debug("usersGet");
        List<UserDto> userDTOList = userService.findAll();
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> userGet(@PathVariable UUID id) {
        log.debug("userGet");
        UserDto user = userService.findByUUID(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/user/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> userPatch(@PathVariable UUID id, @RequestBody UserCrudDto userDTO) {
        log.debug("userPut");
        UserDto updateUser= userService.updateUser(userDTO,id);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/user/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> userDelete(@PathVariable UUID id) {
        log.debug("REST request to delete user with ID {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/user/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @RequestBody PasswordUpdateDto dto) {
        userService.updatePassword(id, dto);
        return ResponseEntity.noContent().build();
    }

}
