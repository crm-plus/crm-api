package com.main.server.model.organization;

import com.main.server.model.BaseEntity;
import com.main.server.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "organization_roles")
@Data
@EqualsAndHashCode(callSuper = false)
public class OrganizationRole extends BaseEntity {

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrganizationRoleType organizationRoleType;

    @EqualsAndHashCode.Exclude
    @OneToOne()
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @EqualsAndHashCode.Exclude
    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
