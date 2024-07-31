package com.demo.studycode.security;

import com.demo.studycode.model.User;
import com.demo.studycode.repository.AuthRepository;
import com.demo.studycode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
//  UserRepository -> User Entity 접근 -> Authority가 추가된 CustomUserDetails 객체를 반환

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = this.authRepository.findByEmail(email);
        return new CustomUserDetails(user);    // -> User entity + Authority
    }

}