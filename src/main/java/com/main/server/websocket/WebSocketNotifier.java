package com.main.server.websocket;

import com.main.server.model.Invitation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
@RequiredArgsConstructor
public class WebSocketNotifier {

    private static final String INVITATION_UPDATE = "/topic/updates/user/%s/invitation";

    private final SimpMessagingTemplate messagingTemplate;

    public void notifyOnInvitationUpdate(Invitation invitation) {
        String updateUrl = String.format(INVITATION_UPDATE, invitation.recipient().credential().email());
        messagingTemplate.convertAndSend(
                updateUrl,
                invitation
        );
    }
}
