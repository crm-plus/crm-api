package com.main.server.model;

import com.main.server.model.organization.Organization;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "category")
public class Category extends BaseEntity {

    @NotBlank
    @Column(name = "name", nullable = false, unique = true, length = 37)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;
}
