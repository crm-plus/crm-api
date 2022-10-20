package com.main.server.security.service.impl;

import com.main.server.model.Credential;
import com.main.server.model.RefreshToken;
import com.main.server.repository.CredentialRepository;
import com.main.server.repository.RefreshTokenRepository;
import com.main.server.security.JwtTokenProvider;
import com.main.server.security.exception.JwtAuthenticationException;
import com.main.server.security.model.AuthResponse;
import com.main.server.security.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtAuthenticationServiceImpl implements AuthenticationService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final CredentialRepository credentialRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponse authenticate(Credential credential) {
        // Authenticates user
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credential.email(),
                    credential.password()
            ));
        } catch (DisabledException e) {
            throw new JwtAuthenticationException("Account is disabled", HttpStatus.UNAUTHORIZED);
        } catch (BadCredentialsException e) {
            throw new JwtAuthenticationException("Credential is invalid", HttpStatus.UNAUTHORIZED);
        }

        return generateAuthResponse(credential.email());
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        RefreshToken existedRefreshToken = getRefreshToken(refreshToken);

        if(!existedRefreshToken.refreshToken().equals(refreshToken)) {
            throw new JwtAuthenticationException("Refresh token is invalid");
        }

        jwtTokenProvider.validateToken(refreshToken);
        refreshTokenRepository.delete(existedRefreshToken);

        return generateAuthResponse(existedRefreshToken.credential().email());
    }

    private AuthResponse generateAuthResponse(String username) {
        // Creates token and refresh token
        String token = jwtTokenProvider.createToken(username, "USER"); // TODO add real role
        String refreshToken = jwtTokenProvider.createRefreshToken(username);

        // Finds credential
        Credential existedCredential = credentialRepository
                .findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Credential does not exist"));

        RefreshToken existedRefreshToken = null;

        try {
            existedRefreshToken = getRefreshToken(refreshToken);
        } catch (ResourceNotFoundException ignored) {
        }

        // Checks if refresh token exist then update it
        if (existedRefreshToken != null) {
            existedRefreshToken.refreshToken(refreshToken);
            refreshTokenRepository.save(existedRefreshToken);
        } else {
            // Creates refresh token entity if it not exist
            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.refreshToken(refreshToken);
            refreshTokenEntity.credential(existedCredential);
            refreshTokenRepository.save(refreshTokenEntity);
        }

        // Creates authentication response
        AuthResponse authResponse = new AuthResponse();
        authResponse.token(token);
        authResponse.refreshToken(refreshToken);
        authResponse.username(existedCredential.email());
        return authResponse;
    }

    private RefreshToken getRefreshToken(String refreshToken) {
        return refreshTokenRepository
                .findByRefreshToken(refreshToken)
                .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("Refresh token with uuid {%s} does not exist", refreshToken)
                        )
                );

    }
}
