package com.main.server.service.impl;

import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.Invitation;
import com.main.server.model.InvitationState;
import com.main.server.model.Organization;
import com.main.server.model.OrganizationRole;
import com.main.server.model.OrganizationRoleType;
import com.main.server.model.User;
import com.main.server.repository.InvitationRepository;
import com.main.server.repository.OrganizationRepository;
import com.main.server.repository.OrganizationRoleRepository;
import com.main.server.repository.UserRepository;
import com.main.server.service.InvitationService;
import com.main.server.websocket.WebSocketNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class InvitationServiceImpl extends AbstractService implements InvitationService {
    private final InvitationRepository invitationRepository;
    private final OrganizationRoleRepository organizationRoleRepository;

    private final WebSocketNotifier webSocketNotifier;

    public InvitationServiceImpl(
            UserRepository userRepository,
            OrganizationRepository organizationRepository,
            InvitationRepository invitationRepository,
            OrganizationRoleRepository organizationRoleRepository,
            WebSocketNotifier webSocketNotifier) {

        super(userRepository, organizationRepository);
        this.invitationRepository = invitationRepository;
        this.organizationRoleRepository = organizationRoleRepository;

        this.webSocketNotifier = webSocketNotifier;
    }

    @Override
    public Invitation inviteUserToOrganization(Long organizationId, String email) {
        log.info(
                "Send invitation to user with email '{}' from organization with id '{}'",
                email,
                organizationId
        );
        User recipient = findUserByEmail(email);
        User sender = getAuthenticatedUser();

        Organization organization = findOrganization(organizationId);

        Invitation invitation = new Invitation();
        invitation.recipient(recipient);
        invitation.organization(organization);
        invitation.sender(sender);
        invitation.state(InvitationState.PENDING);
        invitation.createdAt(new Date());

        Invitation savedInvitation = invitationRepository.save(invitation);

        webSocketNotifier.notifyOnInvitationUpdate(savedInvitation);
        return savedInvitation;
    }

    @Override
    public List<Invitation> getUserInvitations(String userEmail, int page, int size) {
        User user = findUserByEmail(userEmail);
        Pageable pageable = PageRequest.of(page, size);
        return invitationRepository.findAllByRecipientAndState(user, InvitationState.PENDING, pageable);
    }

    @Override
    @Transactional
    public Invitation acceptInvitation(Long invitationId) {
        User user = getAuthenticatedUser();
        Invitation invitation = findInvitationByIdAndUser(invitationId, user);
        invitation.state(InvitationState.ACCEPTED);

        User newMember = invitation.recipient();

        OrganizationRole organizationRole = new OrganizationRole();
        organizationRole.organization(invitation.organization());
        organizationRole.user(newMember);
        organizationRole.organizationRoleType(OrganizationRoleType.SPECTATOR);
        organizationRoleRepository.save(organizationRole);
        newMember.organizationRoles().add(organizationRole);

        invitation.organization().members().add(newMember);
        //TODO add notification that user accept invitation
        return invitationRepository.save(invitation);
    }

    @Override
    public Invitation declineInvitation(Long invitationId) {
        User user = getAuthenticatedUser();
        Invitation invitation = findInvitationByIdAndUser(invitationId, user);
        invitation.state(InvitationState.DECLINED);

        //TODO add notification that user decline invitation
        return invitationRepository.save(invitation);
    }

    @Override
    public List<Invitation> getOrganizationInvitations(Long organizationId, int page, int size) {
        Organization organization = findOrganization(organizationId);
        Pageable pageable = PageRequest.of(page, size);
        return invitationRepository.findAllByOrganizationAndState(organization, InvitationState.PENDING, pageable);
    }

    private Invitation findInvitationByIdAndUser(Long invitationId, User user) {
        return invitationRepository.findByIdAndRecipient(invitationId, user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(
                                "Invitation by id '%s' not found",
                                invitationId
                        )
                ));
    }
}
