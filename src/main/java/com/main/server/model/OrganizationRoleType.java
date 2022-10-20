package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OrganizationRoleType {
    CREATOR("Creator", OrganizationPermissionType.DELETE, OrganizationPermissionType.EDIT, OrganizationPermissionType.VIEW),
    ADMIN("Admin", OrganizationPermissionType.EDIT, OrganizationPermissionType.VIEW),
    SPECTATOR("Spectator", OrganizationPermissionType.VIEW);

    @JsonProperty("name")
    private final String text;
    @JsonProperty("organizationPermissionTypes")
    private final OrganizationPermissionType[] organizationPermissionTypes;

    OrganizationRoleType(String text, OrganizationPermissionType... organizationPermissionTypes) {
        this.text = text;
        this.organizationPermissionTypes = organizationPermissionTypes;
    }
}
