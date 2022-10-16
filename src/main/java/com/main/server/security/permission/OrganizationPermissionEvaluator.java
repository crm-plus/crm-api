package com.main.server.security.permission;

import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.organization.OrganizationRole;
import com.main.server.model.organization.OrganizationRoleType;
import com.main.server.model.User;
import com.main.server.repository.OrganizationRoleRepository;
import com.main.server.repository.UserRepository;
import com.main.server.security.exception.ResourceForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class OrganizationPermissionEvaluator implements PermissionEvaluator {
    private final OrganizationRoleRepository organizationRoleRepository;
    private final UserRepository userRepository;

    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }

        if (targetDomainObject instanceof String) {
            User user = userRepository
                    .getUserByCredentialEmail(auth.getName())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("User with %s username not found", auth.getName())
                    ));
            OrganizationRole organizationRole = organizationRoleRepository
                    .findByOrganizationNameAndUserId((String) targetDomainObject, user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            String.format("For user '%s' and organization '%s' not found any roles", auth.getName(), targetDomainObject)
                    ));

            OrganizationRoleType roleType = organizationRole.getOrganizationRoleType();
            Arrays.stream(roleType.getOrganizationPermissionTypes())
                    .filter((permissionType) -> permissionType.getName().equals(permission))
                    .findFirst()
                    .orElseThrow(() -> new ResourceForbiddenException(
                            String.format("User '%s' do not have permission '%s' to this resource", auth.getName(), permission)
                    ));
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        // Not needed method
        return false;
    }

}
