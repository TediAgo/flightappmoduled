package com.flightapp.service.impl;

import com.flightapp.dto.UserDTO;
import com.flightapp.entity.UserEntity;
import com.flightapp.enums.Role;
import com.flightapp.exception.NotFoundException;
import com.flightapp.mapper.UserConverter;
import com.flightapp.repository.UserRepository;
import com.flightapp.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImplementation.class);

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public UserDTO getUser(Integer id) {
        if (userRepository.findById(id).isEmpty() || userRepository.findById(id).get().getValidity().equals(Boolean.FALSE)) {
            LOGGER.info("User not found.");
            throw new NotFoundException("User not found.");
        }
        return UserConverter.convertUserEntityToDTO(userRepository.findById(id).get());
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().equals(Role.USER) && user.getValidity().equals(Boolean.TRUE))
                .map(UserConverter::convertUserEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAdmins() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole().equals(Role.ADMIN) && user.getValidity().equals(Boolean.TRUE))
                .map(UserConverter::convertUserEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        UserEntity user = UserEntity.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .validity(Boolean.TRUE)
                .build();

        user.setRole(Role.USER);
        userRepository.save(user);
        return UserConverter.convertUserEntityToDTO(user);
    }
}
