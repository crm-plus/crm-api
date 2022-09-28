package com.main.server.service;

import com.main.server.model.Invitation;

public interface InvitationService {

    public Invitation inviteUserToOrganization(Long organizationId, Long userId);
}
