package com.main.server.service;

import com.main.server.model.organization.Organization;
import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.User;

import java.util.List;

public interface OrganizationService {

    Organization save(Organization organization) throws ResourceAlreadyExistException;

    List<Organization> getAll();

    List<Organization> getAllOrganizationUserRelated();

    Organization getById(Long id) throws ResourceNotFoundException;

    Organization delete(Long id) throws ResourceNotFoundException;

    Organization update(Long id, Organization organization) throws ResourceNotFoundException, ResourceAlreadyExistException;

    List<Organization> findByName(String name);

    List<User> getAllMembers(Long id, int page, int size);

    List<User> getAllPendingMembers(Long organizationId, Integer page, Integer size);
}
