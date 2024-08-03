package com.demo.studycode.service;

import com.demo.studycode.dto.AuthDTO;
import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.model.User;
import com.demo.studycode.repository.AuthRepository;
import com.demo.studycode.repository.UserRepository;
import com.demo.studycode.security.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService { // 로그인 및 회원가입, 토큰의 만료기간 등에 관여

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder pwEncoder;

    @Autowired
    private ModelMapper modelMapper;

    // 이메일 중복검사
//    private void testDuplicatedEmail(UserDTO dto) {
//        String email = dto.getEmail();
//        User existedUser = authRepository.findByEmail(email).orElse(null);
//        if (existedUser != null) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }

    @Transactional
    public Long signUp(UserDTO dto) {
        System.out.println("333 " + dto);
        try {
            // 비밀번호 암호화 -> User entity 생성
            String encodedPwd = pwEncoder.encode(dto.getPasswd());
            dto.setPasswd(encodedPwd);
            User user = modelMapper.map(dto, User.class);

            System.out.println("3-1 " + user + " / " + user.getEmail());
            // DB에 entity 저장
            // save() : entity가 존재하는 경우 UPDATE, 존재하지 않는 경우INSERT 실행
            userRepository.save(user);
            return user.getId();
        } catch (Exception e) {
            throw new RuntimeException("error");
        }
    }

    // 로그인
    public String signIn(UserDTO request) {
        String email = request.getEmail();
        String passwd = request.getPasswd();
        Optional<User> user = authRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");
        String savedPwd = user.get().getPasswd();
        // 암호화된 비밀번호를 디코딩한 값과 입력한 값이 다르면 null 반환
        if (!pwEncoder.matches(passwd, savedPwd)) {
            System.out.println("*** 확인? " + user.get().getPasswd());
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }
        AuthDTO dto = modelMapper.map(user, AuthDTO.class);

        String accessToken = jwtUtil.createAccessToken(dto);
        return accessToken;
    }
//
//    public UserDTO updateToken(UserDTO dto, String refreshToken) {
//        dto.setToken(refreshToken);
//        return dto;
//    }

}
