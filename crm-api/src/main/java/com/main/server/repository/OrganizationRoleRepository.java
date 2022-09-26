package com.main.server.repository;

import com.main.server.model.organization.OrganizationRole;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrganizationRoleRepository extends CrudRepository<OrganizationRole, Long> {

    Optional<OrganizationRole> findByOrganizationIdAndUser(Long organizationId, Long userId);

    Optional<OrganizationRole> findByOrganizationNameAndUserId(String organizationName, Long userId);
}
