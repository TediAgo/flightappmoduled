package com.flightapp.mapper;

import com.flightapp.dto.UserDTO;
import com.flightapp.entity.UserEntity;

public class UserConverter {

    public static UserDTO convertUserEntityToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());

        return userDTO;
    }
}
