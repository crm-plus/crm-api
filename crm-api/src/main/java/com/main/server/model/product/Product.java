package com.main.server.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.server.model.BaseEntity;
import com.main.server.model.organization.Organization;
import com.main.server.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseEntity {
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    private double price;
    private double primeCost;

    @OneToMany
    @JoinColumn(name = "product_tag_id", referencedColumnName = "id")
    private Set<ProductTag> tags;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    private int availableQuantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany
    @JoinColumn(name = "custom_parameter-id", referencedColumnName = "id")
    private List<Parameter> customParameters;
}
