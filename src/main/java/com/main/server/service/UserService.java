package com.main.server.service;

import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.Credential;
import com.main.server.model.User;

import java.util.List;

public interface UserService {

    User getUser(Long id);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User saveUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    void register(Credential credential);

}
