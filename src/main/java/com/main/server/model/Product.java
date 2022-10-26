package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseEntity {

    @NotBlank
    @Column(name = "name", nullable = false, length = 37)
    private String name;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @NotNull
    @Column(name = "price", nullable = false)
    private double price;

    @NotNull
    @Column(name = "prime_cost", nullable = false)
    private double primeCost;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "products_tags",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_tag_id")}
    )
    private Set<ProductTag> tags;

    //todo: image

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @NotNull
    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deleted_by")
    private User deletedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Parameter> customParameters;
}
