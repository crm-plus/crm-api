package com.main.server.repository;


import com.main.server.model.Organization;
import com.main.server.model.OrganizationRole;
import com.main.server.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrganizationRoleRepository extends CrudRepository<OrganizationRole, Long> {
    Optional<OrganizationRole> findByOrganizationAndUser(Organization organization, User user);
}
