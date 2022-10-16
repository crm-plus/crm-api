package com.main.server.model.product;

import com.main.server.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = false)
public class Parameter extends BaseEntity {

    @NotBlank
    @Column(name = "name", nullable = false, unique = true, length = 37)
    private String name;

    @NotBlank
    @Column(name = "units", nullable = false, unique = true, length = 37)
    private String units;

    @NotBlank
    @Column(name = "value", nullable = false, unique = true, length = 37)
    private String value;
}
