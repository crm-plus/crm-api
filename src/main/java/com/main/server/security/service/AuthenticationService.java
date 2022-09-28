package com.main.server.security.service;

import com.main.server.model.Credential;
import com.main.server.security.model.AuthResponse;

import java.util.Map;

public interface AuthenticationService {

    AuthResponse authenticate(Credential credential);

    AuthResponse refreshToken(String refreshTokenUuid);
}
