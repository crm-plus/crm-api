package com.main.server.repository;

import com.main.server.model.organization.Organization;
import com.main.server.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    Optional<Organization> findByName(String name);

    List<Organization> findAllByDeletedByIsNull();

    List<Organization> findAllByNameLikeAndDeletedByNullAndIsPrivateFalse(String name);

    List<Organization> findAllByCreatedBy(User user);
}
