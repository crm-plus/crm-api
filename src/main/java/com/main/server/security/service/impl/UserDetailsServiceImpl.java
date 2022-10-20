package com.main.server.security.service.impl;

import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.Credential;
import com.main.server.repository.CredentialRepository;
import com.main.server.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    @Autowired
    public UserDetailsServiceImpl(CredentialRepository credentialRepository) {
        this.credentialRepository = credentialRepository;
    }

    public UserDetails loadUserByUsername(String username) {
        Credential credential = credentialRepository
                .findByEmail(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format(
                                        "User by username '%s' does not exist",
                                        username
                                )
                        ));
        return SecurityUser.fromUser(credential.user());
    }
}
