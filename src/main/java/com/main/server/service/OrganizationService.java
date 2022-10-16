package com.main.server.service;

import com.main.server.model.Organization;
import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.exception.ResourceNotFoundException;

import java.util.List;

public interface OrganizationService {

    Organization save(Organization organization) throws ResourceAlreadyExistException;

    List<Organization> getAll();

    List<Organization> getAllOrganizationUserRelated();

    Organization getById(Long id) throws ResourceNotFoundException;

    Organization delete(Long id) throws ResourceNotFoundException;

    Organization update(Long id, Organization organization) throws ResourceNotFoundException, ResourceAlreadyExistException;

    List<Organization> findByName(String name);
}
