package com.example.userservice.controller;

import com.example.userservice.dao.User;
import com.example.userservice.model.AuthenticationRequest;
import com.example.userservice.model.AuthenticationResponse;
import com.example.userservice.model.RegistrationRequest;
import com.example.userservice.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
        AuthenticationResponse authenticate = authenticationService.authenticate(request);
        return ResponseEntity.ok(authenticate);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistrationRequest request) throws Exception {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }
}
