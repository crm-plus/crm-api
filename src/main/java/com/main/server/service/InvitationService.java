package com.main.server.service;

import com.main.server.model.Invitation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InvitationService {

    Invitation inviteUserToOrganization(Long organizationId, String email);

    List<Invitation> getUserInvitations(String userEmail, int page, int size);

    Invitation acceptInvitation(Long invitationId);

    Invitation declineInvitation(Long invitationId);

    List<Invitation> getOrganizationInvitations(Long organizationId, int page, int size);
}
