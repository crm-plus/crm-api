package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.main.server.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "tags")
public class ProductTag extends BaseEntity {

    @NotBlank
    @JsonProperty("name")
    @Column(name = "name", nullable = false, unique = true, length = 37)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Product> products;
}
