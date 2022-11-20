package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.main.server.model.organization.Organization;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true)
@Table(name = "users")
public class User extends BaseEntity {

    @JsonProperty("firstName")
    @Column(name = "first_name")
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "last_name")
    private String lastName;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credential_id", referencedColumnName = "id")
    private Credential credential;

    @JsonProperty("birthDate")
    @Column(name = "birth_date")
    private Date birthDate;

    @JsonProperty("residentialAddress")
    @Column(name = "residential_address")
    private String residentialAddress;

    @JsonProperty("isDeleted")
    @Column(name = "deleted")
    private boolean isDeleted;

    @JsonProperty("sex")
    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "organizations_users",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "organization_id")}
    )
    private Set<Organization> organizations;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient")
    private Set<Invitation> invitations;

    @JsonProperty("organizationRoles")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.MERGE})
    private Set<OrganizationRole> organizationRoles;

    @Transient
    private List<Long> roleIds;

    @JsonProperty("organizationRole")
    public OrganizationRole organizationRole() {
        return organizationRoles.stream().findFirst().orElseGet(() -> null);
    }

    public void addRole(Role role) {
        if(roles == null) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }

    public void clearRoles() {
        roles = new HashSet<>();
    }

    @JsonProperty("email")
    public String getEmail(){
        return credential.email();
    }

    @JsonProperty("birthDate")
    public String convertDate(){
        if(birthDate == null) {
            return "";
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(birthDate);
    }

}
