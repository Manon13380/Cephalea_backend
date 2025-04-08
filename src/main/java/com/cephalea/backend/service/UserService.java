package com.cephalea.backend.service;

import com.cephalea.backend.dto.UserCrudDto;
import com.cephalea.backend.dto.UserDto;
import com.cephalea.backend.entity.UserEntity;
import com.cephalea.backend.mapper.UserDTOMapper;
import com.cephalea.backend.repository.UserRepository;
import com.cephalea.backend.security.PasswordHasher;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;

import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    //Read all Users
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        log.debug("Find all users");
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> usersDTO = users.stream().map(userDTOMapper::toDTO).toList();
        log.debug("FindAll- Found {} users", usersDTO.size());
        log.debug("FindAll- got list {}", usersDTO);
        return usersDTO;
    }

    //Read One User
    @Transactional(readOnly = true)
    public UserDto findByUUID(UUID id) {
        log.debug("Find user by UUID {}", id);
        return userRepository.findById(id)
                .map(userDTOMapper::toDTO)
                .map(userDto -> {
                    log.debug("Find user by UUID {}", userDto);
                    return userDto;
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with UUID: " + id));

    }

    //Create User
    public UserDto createUser(UserCrudDto userDto) {
        log.debug("Create user {}", userDto);
        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.debug("User with email {} already exists", userDto.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with email " + userDto.getEmail() + " already exists.");
        }
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            log.debug("Password does not match confirm password");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match confirm password");
        }
        //Map DTO to entity
        UserEntity userEntity = userDTOMapper.toEntity(userDto);

        //Hash password
        userEntity.setPassword(PasswordHasher.hashPassword(userDto.getPassword()));

        //Save User
        UserEntity savedUserEntity = userRepository.save(userEntity);
        log.debug("Create user {}", savedUserEntity);
        return userDTOMapper.toDTO(savedUserEntity);
    }

    //Update User
    public UserDto updateUser(UserCrudDto userDto, UUID id) {
        log.debug("Update user {}", userDto);

        //Find Existing Email
        UserEntity userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        boolean checkPassword = PasswordHasher.checkPassword(userDto.getPassword(), userToUpdate.getPassword());

        if (!userDto.getEmail().equals(userToUpdate.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
            log.error("User with email {} already exists", userDto.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email " + userDto.getEmail() + " already exists.");
        }

        // check Password
        if (!checkPassword ) {
            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                log.error("Password does not match confirm password");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password does not match confirm password");
            }
            userToUpdate.setPassword(PasswordHasher.hashPassword(userDto.getPassword()));
        }
        userToUpdate.setEmail(userDto.getEmail());
        userToUpdate.setFirstName(userDto.getFirstName());
        userToUpdate.setName(userDto.getName());
        userToUpdate.setBirthDate(userDto.getBirthDate());
        userToUpdate.setDoctor(userDto.getDoctor());
        userToUpdate.setNeurologist(userDto.getNeurologist());

        UserEntity updatedUserEntity = userRepository.save(userToUpdate);
        log.debug("Update user {}", updatedUserEntity);
        return userDTOMapper.toDTO(updatedUserEntity);
    }

    //Delete User
    public void deleteUser(UUID id) {
        log.debug("Delete user {}", id);
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        log.debug("Delete user {}", id);
    }
}
