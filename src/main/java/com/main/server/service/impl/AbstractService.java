package com.main.server.service.impl;

import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.organization.Organization;
import com.main.server.model.User;
import com.main.server.repository.OrganizationRepository;
import com.main.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractService {
    protected final UserRepository userRepository;
    protected final OrganizationRepository organizationRepository;
    private static final String USER_NOT_FOUND_BY_ID = "User not found by id: '%s'";
    private static final String USER_NOT_FOUND_BY_EMAIL = "User not found by email: '%s'";

    /**
     * Finds user by id
     *
     * @param id The Id of user
     * @return user with id
     */
    public User findUser(Long id) {
        log.info("Find user by id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        USER_NOT_FOUND_BY_ID,
                        id
                )));
    }

    /**
     * Finds Organization by id
     *
     * @param id The organization's id
     * @return organization
     */
    public Organization findOrganization(Long id) {
        log.info("Find organization by id: {}", id);
        return organizationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("Organization with id %d not found", id)
                        )
                );
    }

    /**
     * Get user based on authentication
     * @return user based on authentication
     */
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findUserByEmail(authentication.getName());
    }

    /**
     * Finds User by email
     * @param email The user's id
     * @return user by id
     */
    public User findUserByEmail(String email) {
        log.info("Find user by email: {}", email);
        return userRepository.getUserByCredentialEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(USER_NOT_FOUND_BY_EMAIL, email)
                ));
    }
}
