package com.example.userservice.service;

import com.example.userservice.dao.MyUserDetails;
import com.example.userservice.dao.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> users = repository.findByUserName(username);
        if (users.isPresent()) {
            return new MyUserDetails(users.get());
        } else {
            throw new UsernameNotFoundException("bad credentials");
        }
    }
}

