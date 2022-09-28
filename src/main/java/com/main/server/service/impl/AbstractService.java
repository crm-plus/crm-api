package com.main.server.service.impl;

import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.Organization;
import com.main.server.model.User;
import com.main.server.repository.OrganizationRepository;
import com.main.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractService {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private static final String USER_NOT_FOUND_BY_ID = "User not found by id: '%s'";

    /**
     * Returns user by id
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
     * Returns Organization by id
     * @param id The organization's id
     * @return organization
     */
    public Organization findOrganization(Long id){
        log.info("Find organization by id: {}", id);
        return organizationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("Organization with id %d not found", id)
                        )
                );
    }
}
