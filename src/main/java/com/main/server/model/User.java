package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @Transient
    private List<Long> roleIds;

    @JsonProperty("roleNames")
    public List<String> roleNames() {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
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
        return new SimpleDateFormat("yyyy-MM-dd").format(birthDate);
    }
}
