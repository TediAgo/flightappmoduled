package com.flightapp.mapper;

import com.flightapp.dto.UserDTO;
import com.flightapp.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToDTO(UserEntity user);

    UserEntity userDTOToUser(UserDTO userDTO);
}
