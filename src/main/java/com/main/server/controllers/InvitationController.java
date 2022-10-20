package com.main.server.controllers;

import com.main.server.model.Invitation;
import com.main.server.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = {"/api"})
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping("/users/{userEmail}/invitations")
    public List<Invitation> getUserInvitations(@PathVariable String userEmail, @RequestParam int page, @RequestParam int size) {
        return invitationService.getUserInvitations(userEmail, page, size);
    }

    @GetMapping("/organizations/{organizationId}/invitations")
    public List<Invitation> getOrganizationInvitations(@PathVariable Long organizationId, @RequestParam int page, @RequestParam int size) {
        return invitationService.getOrganizationInvitations(organizationId, page, size);
    }

    @PostMapping("/organizations/{organizationId}/user/{email}/invite")
    public Invitation inviteUserToOrganization(@PathVariable Long organizationId, @PathVariable String email) {
        return invitationService.inviteUserToOrganization(organizationId, email);
    }

    @PostMapping("/invitations/{invitationId}/accept")
    public Invitation acceptInviteToOrganization(@PathVariable Long invitationId) {
        return invitationService.acceptInvitation(invitationId);
    }

    @PostMapping("/invitations/{invitationId}/decline")
    public Invitation declineInviteToOrganization(@PathVariable Long invitationId) {
        return invitationService.declineInvitation(invitationId);
    }
}
