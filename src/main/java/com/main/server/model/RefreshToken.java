package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "refresh_tokens")
public class RefreshToken extends BaseEntity {

    @NotNull
    @JsonProperty("refreshToken")
    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;

    @JsonIgnore
    @OneToOne()
    @JoinColumn(name = "credential_id", referencedColumnName = "id")
    private Credential credential;
}
