package com.main.server.service;

import com.main.server.dto.UserDTO;
import com.main.server.dto.UserData;
import com.main.server.exception.ResourceAlreadyExist;

import java.util.List;

public interface UserService {

    UserDTO getUser(Long id) throws ResourceAlreadyExist;

    List<UserDTO>  getAllUsers();

    UserDTO saveUser(UserDTO user);

    UserDTO updateUser(Long id, UserDTO user);

    void deleteUser(Long id);
}