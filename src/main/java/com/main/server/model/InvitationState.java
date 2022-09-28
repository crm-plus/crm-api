package com.main.server.model;

public enum InvitationState {
    ACCEPTED("accepted"),
    PENDING("pending"),
    DECLINED("declined");

    private String text;

    InvitationState(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
