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
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

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

    @Override
    public Long signUp(UserDTO dto) {
        try {
            // 비밀번호 암호화 -> User entity 생성
            String encodedPwd = pwEncoder.encode(dto.getPasswd());
            dto.setPasswd(encodedPwd);
            User user = modelMapper.map(dto, User.class);

            // DB에 entity 저장
            // save() : entity가 존재하는 경우 UPDATE, 존재하지 않는 경우INSERT 실행
            userRepository.save(user);
            return user.getId();
        } catch (Exception e) {
            throw new RuntimeException("error");
        }
    }

    @Override
    public String signIn(UserDTO request) {
        String email = request.getEmail();
        String passwd = request.getPasswd();
        Optional<User> user = authRepository.findByEmail(email);
        if(user == null)
            throw new UsernameNotFoundException("이메일이 존재하지 않습니다.");

        // 암호화된 비밀번호를 디코딩한 값과 입력한 값이 다르면 null 반환
        if(!pwEncoder.matches(passwd, request.getPasswd()))
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");

        AuthDTO dto = modelMapper.map(user, AuthDTO.class);

        String accessToken = jwtUtil.createAccessToken(dto);
        return accessToken;
    }
}
