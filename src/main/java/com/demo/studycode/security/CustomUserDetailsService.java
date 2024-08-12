package com.demo.studycode.security;

import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.model.User;
import com.demo.studycode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
//  UserDetailsService : Spring Security에서 사용자 정보를 가져오기 위한 메서드를 정의
//  사용자의 인증 정보를 DB 다른 데이터 저장소에서 가져와서 UserDetails 객체로 변환하여 제공하는 역할
//  UserRepository -> User Entity 접근 -> Authority가 추가된 CustomUserDetails 객체를 반환

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByEmail(email);
        UserDTO dto = modelMapper.map(user, UserDTO.class);
        return new CustomUserDetails(dto);
    }

}