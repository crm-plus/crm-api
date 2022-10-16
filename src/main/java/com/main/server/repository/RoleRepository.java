package com.main.server.repository;

import com.main.server.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {

    Optional<Role> findByName(String name);

}
