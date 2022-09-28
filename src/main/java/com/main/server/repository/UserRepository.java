package com.main.server.repository;

import com.main.server.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("FROM users")
    Set<User> getAll();

    Optional<User> getUserByCredentialEmail(String email);
}
