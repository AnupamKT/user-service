package com.example.userservice.converter;

import com.example.userservice.dao.User;
import com.example.userservice.model.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestConverter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User convert(RegistrationRequest request) {
        User user= new User();
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());
        user.setActive(true);
        return user;
    }
}
