package com.main.server.model.organization;

public enum OrganizationRoleType {
    CREATOR(OrganizationPermissionType.DELETE, OrganizationPermissionType.EDIT, OrganizationPermissionType.VIEW),
    ADMIN(OrganizationPermissionType.EDIT, OrganizationPermissionType.VIEW),
    SPECTATOR(OrganizationPermissionType.VIEW);


    private final OrganizationPermissionType[] organizationPermissionTypes;

    OrganizationRoleType(OrganizationPermissionType... organizationPermissionTypes) {
        this.organizationPermissionTypes = organizationPermissionTypes;
    }

    public OrganizationPermissionType[] getOrganizationPermissionTypes() {
        return organizationPermissionTypes;
    }
}
