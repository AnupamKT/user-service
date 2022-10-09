package com.example.userservice.dao;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private String userName;
    private String password;
    private boolean isActive;
    private Set<GrantedAuthority> roles;


    public MyUserDetails(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.isActive = user.isActive();
        this.roles = Arrays.stream(user.getRoles().split(",")).map(role->new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
