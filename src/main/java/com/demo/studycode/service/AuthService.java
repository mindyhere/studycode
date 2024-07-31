package com.demo.studycode.service;

import com.demo.studycode.dto.AuthDTO;
import com.demo.studycode.dto.ResponseDTO;
import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.model.User;
import com.demo.studycode.repository.AuthRepository;
import com.demo.studycode.repository.UserRepository;
import com.demo.studycode.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
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
    private PasswordEncoder passwdEncoder;

    @Autowired
    private JwtTokenProvider jwtProvider;


    // 회원가입
    @Transactional
    public ResponseDTO signUp(UserDTO dto) {
        String email = dto.getEmail();
        final User EXISTED_USER = authRepository.findByEmail(email).orElse(null);

        if (EXISTED_USER != null) {
            return ResponseDTO.setFailed("이미 사용 중인 email 입니다.");
        }

        try {
            // 비밀번호 암호화 -> User entity 생성
            String encodedPwd = passwdEncoder.encode(dto.getPasswd());
            dto.setPasswd(encodedPwd);
            User user = new User(dto);

            // DB에 entity 저장
            authRepository.save(user);
        } catch (Exception e) {
            return ResponseDTO.setFailed("처리 중 문제가 발생했습니다. 잠시 후 다시 이용해주세요.");
        }
        return ResponseDTO.setSuccess("가입이 완료되었습니다.");
    }

    // 로그인
    public ResponseDTO<AuthDTO> signIn(UserDTO dto) {
        String email = dto.getEmail();
        String passwd = dto.getPasswd();

        final User EXISTED_USER = authRepository.findByEmail(email).orElse(null);
        if (EXISTED_USER == null) {
            return ResponseDTO.setFailed("사용자를 찾을 수 없습니다. 이메일/비밀번호 확인 후 다시 시도해주세요.");
        }

        String savedPwd = EXISTED_USER.getPasswd(); // 암호화되어 DB에 저장된 비밀번호
        if (!passwdEncoder.matches(passwd, savedPwd)) { // 입력한 비밀번호와의 일치여부 확인
            return ResponseDTO.setFailed("비밀번호가 일치하지 않습니다.");
        }

        AuthDTO authDto = null;
        try {
            authDto.setId(EXISTED_USER.getId());
            authDto.setEmail(EXISTED_USER.getEmail());
            authDto.setName(EXISTED_USER.getName());
            authDto.setRole(EXISTED_USER.getRole());

            int duration = 3600;     // 1h
            String token = jwtProvider.generateToken(email, duration);

            authDto.setAccessToken(token);
            authDto.setExpireTime(duration);

            return ResponseDTO.setSuccessData("로그인되었습니다.", authDto);

        } catch (Exception e) {
            return ResponseDTO.setFailed("처리 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }
    }

}
