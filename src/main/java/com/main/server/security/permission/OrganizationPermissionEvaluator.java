package com.main.server.security.permission;

import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.Organization;
import com.main.server.model.OrganizationRole;
import com.main.server.model.OrganizationRoleType;
import com.main.server.model.User;
import com.main.server.repository.OrganizationRepository;
import com.main.server.repository.OrganizationRoleRepository;
import com.main.server.repository.UserRepository;
import com.main.server.security.exception.ResourceForbiddenException;
import com.main.server.service.OrganizationService;
import com.main.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class OrganizationPermissionEvaluator implements PermissionEvaluator {
    private final OrganizationRepository organizationRepository;
    private final OrganizationRoleRepository organizationRoleRepository;
    private final UserRepository userRepository;

    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }

        Organization organization = null;

        if (targetDomainObject instanceof Long) {
            organization = organizationRepository.findById((Long) targetDomainObject)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Organization by id %s not found", targetDomainObject)
                    ));
        }

        if (targetDomainObject instanceof String) {
            organization = organizationRepository.findByName((String) targetDomainObject)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("Organization by name %s not found", targetDomainObject)
                    ));
        }

        if (organization == null) {
            return false;
        }


        User user = userRepository
                .getUserByCredentialEmail(auth.getName())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with %s username not found", auth.getName())
                ));

        OrganizationRole organizationRole = organizationRoleRepository
                .findByOrganizationAndUser(organization, user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("For user '%s' and organization '%s' not found any roles", auth.getName(), targetDomainObject)
                ));

        OrganizationRoleType roleType = organizationRole.organizationRoleType();

        Arrays.stream(roleType.getOrganizationPermissionTypes())
                .filter((permissionType) -> permissionType.getName().equals(permission))
                .findFirst()
                .orElseThrow(() -> new ResourceForbiddenException(
                        String.format("User '%s' do not have permission '%s' to this resource", auth.getName(), permission)
                ));

        return true;
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        // Not needed method
        return false;
    }

}
