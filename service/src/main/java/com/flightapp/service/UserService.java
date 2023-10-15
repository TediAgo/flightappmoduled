package com.flightapp.service;

import com.flightapp.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUser(Integer id);

    List<UserDTO> getUsers();

    List<UserDTO> getAdmins();

    UserDTO createUser(UserDTO userDTO);

    /*UserDTO createAdmin(Integer id);*/
}