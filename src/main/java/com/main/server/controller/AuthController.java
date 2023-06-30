package com.main.server.controller;

import com.main.server.model.Credential;
import com.main.server.model.RefreshToken;
import com.main.server.security.model.AuthResponse;
import com.main.server.security.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/auth")
@CrossOrigin
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody @Valid Credential credential) {
        return new ResponseEntity<>(authenticationService.authenticate(credential), HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody @Valid RefreshToken refreshToken) {
        return new ResponseEntity<>(authenticationService.refreshToken(refreshToken.refreshToken()), HttpStatus.OK);
    }
}
