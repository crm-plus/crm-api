package com.main.server.controllers;

import com.main.server.exception.ResourceAlreadyExistException;
import com.main.server.exception.ResourceNotFoundException;
import com.main.server.model.Credential;
import com.main.server.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(path = {"/api/account"})
@AllArgsConstructor
public class AccountController {

    private final UserService userService;

    @RequestMapping("/register")
    public void registration(@RequestBody Credential credential) throws ResourceNotFoundException, ResourceAlreadyExistException {
        userService.register(credential);
    }
}
