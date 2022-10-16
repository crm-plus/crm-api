package com.main.server.service.impl;

import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.Credential;
import com.main.server.model.Role;
import com.main.server.model.RoleTypes;
import com.main.server.model.User;
import com.main.server.repository.CredentialRepository;
import com.main.server.repository.RoleRepository;
import com.main.server.repository.UserRepository;
import com.main.server.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final CredentialRepository credentialRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final String USER_NOT_FOUND_BY_ID = "User not found by id: ";

    /**
     * Return user by its id
     *
     * @throws ResourceNotFoundException if such user in not exists
     */
    @Override
    public User getUser(Long id) {
        log.info("Enter getUser() id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND_BY_ID + id));
    }

    /**
     * Return all user that exists in db
     */
    @Override
    public List<User> getAllUsers() {
        log.info("Enter getAllUsers()");
        return new ArrayList<>(userRepository.getAll());
    }

    /**
     * Save user
     *
     * @return saved user
     * @throws ResourceNotFoundException     if user by current id does not exist
     * @throws ResourceAlreadyExistException if user with the same email already exist
     */
    @Override
    public User saveUser(User user) {
        log.info("Enter saveUser() user: {}", user);

        checkIfEmailExist(user.credential().email());

        for (Long roleId : user.roleIds()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            user.addRole(role);
        }
        return userRepository.save(user);
    }

    /**
     * Update user
     *
     * @throws ResourceNotFoundException     if user by current id does not exist
     * @throws ResourceAlreadyExistException if user with the same email already exist
     */
    @Override
    public User updateUser(Long id, User userRequest) {
        log.info("Enter updateUser() id: {}, userRequest: {}", id, userRequest);

        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format("User by id %s does not exist", id))
                );

        checkIfEmailExist(user.credential().email());

        user.firstName(userRequest.firstName());
        user.lastName(userRequest.lastName());
        user.credential().email(userRequest.credential().email());
        user.credential().password(userRequest.credential().password());
        user.birthDate(userRequest.birthDate());
        user.residentialAddress(userRequest.residentialAddress());
        user.sex(userRequest.sex());
        user.clearRoles();

        for (Long roleId : userRequest.roleIds()) {
            Role role = roleRepository
                    .findById(roleId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Role not found")
                    );
            user.addRole(role);
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Enter deleteUser() id: {}", id);
        User user = userRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format("User with id %s does not exist", id)
                        )
                );
        user.isDeleted(true);
    }

    @Override
    public void register(Credential credential) {
        checkIfEmailExist(credential.email());

        String encodedPassword = encodePassword(credential.password());

        credential.password(encodedPassword);

        User user = new User();

        user.credential(credential);

        Role userRole = getRoleByName(RoleTypes.USER.name());
        user.addRole(userRole);

        credential.user(user);

        credentialRepository.save(credential);
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.getUserByCredentialEmail(email);
        return user.get();
    }

    private void checkIfEmailExist(String email) {
        Optional<Credential> credential = credentialRepository.findByEmail(email);
        if (credential.isPresent()) {
            throw new ResourceAlreadyExistException(
                    String.format("User with the same email {%s} already exist", email)
            );
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private Role getRoleByName(String roleName) {
        return roleRepository
                .findByName(RoleTypes.USER.name())
                .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("Role with name {%s} does not exist", roleName)
                        )
                );
    }
}
