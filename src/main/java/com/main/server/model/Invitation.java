package com.main.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity()
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(fluent = true)
@Table(name = "invitations")
public class Invitation extends BaseEntity {

    @JsonProperty("sender")
    @ManyToOne()
    @JoinColumn(name = "sender_id")
    private User sender;

    @JsonProperty("organization")
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @JsonProperty("recipient")
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "recipient_id")
    private User recipient;


    @Column(name = "created_at")
    private Date createdAt;

    @JsonProperty("state")
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationState state;

}
