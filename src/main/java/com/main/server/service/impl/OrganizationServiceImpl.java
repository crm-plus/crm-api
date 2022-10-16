package com.main.server.service.impl;

import com.main.server.model.InvitationState;
import com.main.server.model.Organization;
import com.main.server.model.OrganizationRole;
import com.main.server.model.OrganizationRoleType;
import com.main.server.model.User;
import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.exception.ResourceNotFoundException;
import com.main.server.repository.OrganizationRepository;
import com.main.server.repository.OrganizationRoleRepository;
import com.main.server.repository.UserRepository;
import com.main.server.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service for crud manipulations with entity {@link} Organization
 */
@Service
@Slf4j
public class OrganizationServiceImpl extends AbstractService implements OrganizationService {

    private OrganizationRoleRepository organizationRoleRepository;

    public OrganizationServiceImpl(
            UserRepository userRepository,
            OrganizationRepository organizationRepository,
            OrganizationRoleRepository organizationRoleRepository) {

        super(userRepository, organizationRepository);
        this.organizationRoleRepository = organizationRoleRepository;
    }

    /**
     * Save organization
     *
     * @return saved organization entity
     * @throws ResourceAlreadyExistException if organization with the same name already exist
     */
    @Override
    public Organization save(Organization organization) throws ResourceAlreadyExistException {
        Optional<Organization> existedOrganization = organizationRepository.findByName(organization.name());
        if (existedOrganization.isPresent()) {
            throw new ResourceAlreadyExistException(
                    String.format("Organization with name %s already exist", organization.name())
            );
        }

        User user = getAuthenticatedUser();

        // Save organization
        organization.createdBy(user);
        organization.createdAt(new Date());
        organization.addMember(user);
        Organization savedOrganization = organizationRepository.save(organization);

        OrganizationRole organizationRole = new OrganizationRole();
        organizationRole.organizationRoleType(OrganizationRoleType.ADMIN);
        organizationRole.organization(savedOrganization);
        organizationRole.user(user);
        organizationRoleRepository.save(organizationRole);

        return savedOrganization;
    }

    /**
     * Get all organizations which are not deleted
     */
    @Override
    public List<Organization> getAll() {
        return organizationRepository.findAllByDeletedByIsNull();
    }

    /**
     * Get organization by id
     *
     * @throws ResourceNotFoundException if organization by current id does not exist
     */
    @Override
    public Organization getById(Long id) throws ResourceNotFoundException {
        return getOrganization(id);
    }

    /**
     * Set Organization deletedBy by current user and save entity
     *
     * @throws ResourceNotFoundException if organization by current id does not exist
     */
    @Override
    public Organization delete(Long id) throws ResourceNotFoundException {
        Organization organization = getOrganization(id);
        User user = new User(); // TODO add real user
        organization.deletedBy(user);
        return organizationRepository.save(organization);
    }

    /**
     * Update organization
     *
     * @throws ResourceNotFoundException     if organization by current id does not exist
     * @throws ResourceAlreadyExistException if organization with the same name already exist
     */
    @Override
    public Organization update(Long id, Organization organization) throws ResourceNotFoundException, ResourceAlreadyExistException {
        Organization existedOrganization = getOrganization(id);

        //Check if rename of organization won`t throw an exception
        Organization existedOrganizationWithSameName = organizationRepository.findByName(organization.name()).orElse(null);
        if (existedOrganizationWithSameName != null) {
            throw new ResourceAlreadyExistException(
                    String.format("Organization with name %s already exist", organization.name())
            );
        }

        existedOrganization.name(organization.name());
        existedOrganization.description(organization.description());
        existedOrganization.updatedAt(new Date());

        return organizationRepository.save(existedOrganization);
    }

    /**
     * Find organization by name that is not private and not deleted
     */
    @Override
    public List<Organization> findByName(String name) {
        return organizationRepository
                .findAllByNameLikeAndDeletedByNullAndIsPrivateFalse(name);
    }

    @Override
    public List<User> getAllMembers(Long id, int page, int size) {
        //TODO add pagination
        return userRepository.findAllMembersByOrganizationId(id);
    }

    @Override
    public List<User> getAllPendingMembers(Long organizationId, Integer page, Integer size) {
        // TODO add pagination
        return userRepository.findAllPendingMembers(organizationId, InvitationState.PENDING);
    }

    @Override
    public List<Organization> getAllOrganizationUserRelated() {
        User user = getAuthenticatedUser();
        return organizationRepository.findAllByCreatedBy(user);
    }

    /**
     * Returns organization by id
     */
    private Organization getOrganization(Long id) throws ResourceNotFoundException {
        return organizationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("Organization with id %d not found", id)
                        )
                );
    }
}
