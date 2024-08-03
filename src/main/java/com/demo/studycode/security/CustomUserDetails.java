package com.demo.studycode.security;

import com.demo.studycode.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// UserDetails: Spring Security에서 제공하는 인증과 관련된 사용자 정보를 추상화한 인터페이스(사용자+권한 정보 제공 기능을 정의)
public class CustomUserDetails implements UserDetails {
    private UserDTO user;

    public CustomUserDetails(UserDTO user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }

    public Long getEmail() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPasswd();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getName() {
        return user.getName();
    }

    public String getPhone() {
        return user.getPhone();
    }

    public String getProfile() {
        return user.getProfile();
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
