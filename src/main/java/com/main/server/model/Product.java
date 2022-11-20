package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.main.server.model.organization.Organization;
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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "products")
public class Product extends BaseEntity {

    @NotBlank
    @JsonProperty("name")
    @Column(name = "name", nullable = false, length = 37)
    private String name;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    private Organization organization;

    @NotNull
    @JsonProperty("price")
    @Column(name = "price", nullable = false)
    private double price;

    @NotNull
    @JsonProperty("primeCost")
    @Column(name = "prime_cost", nullable = false)
    private double primeCost;

    @ManyToMany
    @JsonProperty("tags")
    @JoinTable(
            name = "products_tags",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_tag_id")}
    )
    private Set<ProductTag> tags;

    //todo: image

    @ManyToOne
    @JsonProperty("category")
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @NotNull
    @JsonProperty("availableQuantity")
    @Column(name = "available_quantity", nullable = false)
    private int availableQuantity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deleted_by")
    private User deletedBy;

    @OneToMany(mappedBy = "product")
    @JsonProperty("customParameters")
    private List<Parameter> customParameters;
}
