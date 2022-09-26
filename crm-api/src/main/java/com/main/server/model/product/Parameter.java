package com.main.server.model.product;

import com.main.server.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Parameter extends BaseEntity {
    private String name;
    private String units;
    private String value;
}
