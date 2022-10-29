package com.example.userservice.service;

import com.example.userservice.common.DuplicateUserException;
import com.example.userservice.common.InvalidCredentialsException;
import com.example.userservice.converter.RegistrationRequestConverter;
import com.example.userservice.dao.User;
import com.example.userservice.model.AuthenticationRequest;
import com.example.userservice.model.AuthenticationResponse;
import com.example.userservice.model.RegistrationRequest;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private RegistrationRequestConverter converter;
    @Autowired
    private UserRepository userRepository;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        log.info("start of authenticate method");
        try {
            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(),
                            request.getPassword()));
            log.debug("user {} authenticated successfully", request.getUserName());
        } catch (AuthenticationException e) {
            log.error("user authentication failed for username {}", request.getUserName());
            throw new InvalidCredentialsException("incorrect username or password");
        }
        log.info("End of authenticate method");
        return new AuthenticationResponse(generateToken(request));
    }

    private String generateToken(AuthenticationRequest request) {
        log.info("start of generateToken method");
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
        log.info("End of generateToken method");
        return jwtUtil.generateToken(userDetails);
    }

    public void register(RegistrationRequest request) throws Exception {
        validateIfUserAlreadyExists(request);
        User user = converter.convert(request);
        userRepository.save(user);
        log.info("user {} registered successfully",request.getUserName());
    }

    private void validateIfUserAlreadyExists(RegistrationRequest request) throws DuplicateUserException {
        boolean isExists = userRepository.existsByUserName(request.getUserName());
        if (isExists) {
            log.error("user {} already exists",request.getUserName());
            throw new DuplicateUserException("user already exists");
        }


    }
}
