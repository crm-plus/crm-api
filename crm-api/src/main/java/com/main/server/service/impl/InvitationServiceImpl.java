package com.main.server.service.impl;

import com.main.server.model.Invitation;
import com.main.server.model.InvitationState;
import com.main.server.model.Organization;
import com.main.server.model.User;
import com.main.server.repository.InvitationRepository;
import com.main.server.repository.OrganizationRepository;
import com.main.server.repository.UserRepository;
import com.main.server.service.InvitationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InvitationServiceImpl extends AbstractService implements InvitationService {
    private final InvitationRepository invitationRepository;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public InvitationServiceImpl(
            UserRepository userRepository,
            OrganizationRepository organizationRepository,
            InvitationRepository invitationRepository,
            SimpMessagingTemplate simpMessagingTemplate) {

        super(userRepository, organizationRepository);
        this.invitationRepository = invitationRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public Invitation inviteUserToOrganization(Long organizationId, Long userId) {
        log.info(
                "Send invitation to user with id '{}' from organization with id '{}'",
                userId,
                organizationId
        );
        User user = findUser(userId);
        Organization organization = findOrganization(organizationId);

        Invitation invitation = new Invitation();
        invitation.recipient(user);
        invitation.sender(organization);
        invitation.state(InvitationState.PENDING);

        simpMessagingTemplate.convertAndSendToUser(
                user.credential().email(),
                "/notify",
                invitation
        );

        return invitationRepository.save(invitation);
    }
}
