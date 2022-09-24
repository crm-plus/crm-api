package com.main.server.controllers;

import com.main.server.model.Invitation;
import com.main.server.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = {"/api/organizations/{organizationId}"})
@RequiredArgsConstructor
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/user/{userId}/invite")
    public Invitation inviteUserToOrganization(@PathVariable Long organizationId, @PathVariable Long userId) {
        return invitationService.inviteUserToOrganization(organizationId, userId);
    }
}
