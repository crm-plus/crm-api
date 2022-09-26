package com.main.server.model.product;

import com.main.server.model.BaseEntity;
import com.main.server.model.organization.Organization;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Category extends BaseEntity {
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    //private List<Product> products;
}
