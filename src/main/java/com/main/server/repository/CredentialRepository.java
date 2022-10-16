package com.main.server.repository;

import com.main.server.model.Credential;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialRepository  extends CrudRepository<Credential, Long> {
    Optional<Credential> findByEmail(String email);
}
