package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true)
@Table(name = "organizations")
public class Organization extends BaseEntity {

    @NotBlank
    @JsonProperty("name")
    @Column(name = "name", unique = true, nullable = false, length = 16)
    private String name;

    @JsonProperty("description")
    @Column(name = "description")
    private String description;

    @JsonProperty("createdBy")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by")
    private User deletedBy;

    @JsonProperty("createdAt")
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @JsonProperty("updatedAt")
    @Column(name = "updated_at")
    private Date updatedAt;

    @NotNull
    @JsonProperty("isPrivate")
    @Column(name = "private")
    private Boolean isPrivate;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "organizations_users",
            joinColumns = {@JoinColumn(name = "organization_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> members;

    public void addMember(User user) {
        if(members == null) {
            members = new ArrayList<>();
        }
        members.add(user);
    }
}
