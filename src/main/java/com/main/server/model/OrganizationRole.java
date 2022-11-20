package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.main.server.model.organization.Organization;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "organization_roles")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true)
public class OrganizationRole extends BaseEntity {

    @JsonProperty("roleType")
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrganizationRoleType organizationRoleType;

    @EqualsAndHashCode.Exclude
    @ManyToOne()
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @EqualsAndHashCode.Exclude
    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
