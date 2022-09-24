package com.main.server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity()
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true)
@Table(name = "invitations")
public class Invitation extends BaseEntity {
    @ManyToOne()
    @JoinColumn(name = "organization_id")
    private Organization sender;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User recipient;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationState state;
}
