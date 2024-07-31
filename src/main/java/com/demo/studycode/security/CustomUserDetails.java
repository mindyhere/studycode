package com.demo.studycode.security;

import com.demo.studycode.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {
    private Optional<User> user;

    public CustomUserDetails(Optional<User> user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public Long getId() {
        return user.get().getId();
    }

    @Override
    public String getPassword() {
        return user.get().getPasswd();
    }

    @Override
    public String getUsername() {
        return user.get().getEmail();
    }

    public String getName() {
        return user.get().getName();
    }

    public String getPhone() {
        return user.get().getPhone();
    }

    public String getProfile() {
        return user.get().getProfile();
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
        return true;
    }
}
