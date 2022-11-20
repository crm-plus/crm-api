package com.main.server.model;

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
@Table(name = "custom_parameters")
public class Parameter extends BaseEntity {

    @NotBlank
    @Column(name = "name", nullable = false, unique = true, length = 37)
    private String name;

    @NotBlank
    @Column(name = "units", nullable = false, unique = true, length = 37)
    private String units;

    @NotBlank
    @Column(name = "parameter_value", nullable = false, unique = true, length = 37)
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
}
