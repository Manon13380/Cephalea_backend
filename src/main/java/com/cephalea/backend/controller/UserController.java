package com.cephalea.backend.controller;

import com.cephalea.backend.dto.UserCrudDto;
import com.cephalea.backend.dto.UserDto;
import com.cephalea.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/user")
    public ResponseEntity<UserDto> UserPost(@Valid @RequestBody UserCrudDto userDTO) {
      log.debug("UserPost {}", userDTO);
            UserDto createdUser = userService.createUser(userDTO);
            return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> usersGet() {
        log.debug("usersGet");
        List<UserDto> userDTOList = userService.findAll();
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> userGet(@PathVariable UUID id) {
        log.debug("userGet");
        UserDto user = userService.findByUUID(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<UserDto> userPut(@PathVariable UUID id, @RequestBody UserCrudDto userDTO) {
        log.debug("userPut");
        UserDto updateUser= userService.updateUser(userDTO,id);
        return ResponseEntity.ok(updateUser);
    }

}
