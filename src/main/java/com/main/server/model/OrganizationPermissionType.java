package com.main.server.model;

public enum OrganizationPermissionType {
    DELETE("delete"),
    VIEW("view"),
    EDIT("edit");

    private final String name;

    OrganizationPermissionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
