package com.demo.studycode.service;

import com.demo.studycode.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService { // 로그인 및 회원가입, 토큰의 만료기간 등에 관여
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private AuthRepository authRepository;
//
//    @Autowired
//    private PasswordEncoder passwdEncoder;
//
//    @Autowired
//    private JwtTokenProvider jwtProvider;

    // 이메일 중복검사
//    private void testDuplicatedEmail(UserDTO dto) {
//        String email = dto.getEmail();
//        User existedUser = authRepository.findByEmail(email).orElse(null);
//        if (existedUser != null) {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }
//    }

    // 회원가입
    Long signUp(UserDTO dto);

    // 로그인
    String signIn(UserDTO request);
//        String email = dto.getEmail();
//        String passwd = dto.getPasswd();
//
//        final User EXISTED_USER = authRepository.findByEmail(email).orElse(null);
//        if (EXISTED_USER == null) return ResponseDTO.setFailed("error");
//
//        // 암호화되어 DB에 저장된 비밀번호
//        String savedPwd = EXISTED_USER.getPasswd();
//        // 입력한 비밀번호와의 일치여부 확인
//        if (!passwdEncoder.matches(passwd, savedPwd)) return ResponseDTO.setFailed("error");
//
//        AuthDTO authDto = null;
//        try {
//            authDto.setId(EXISTED_USER.getId());
//            authDto.setEmail(EXISTED_USER.getEmail());
//            authDto.setName(EXISTED_USER.getName());
//            authDto.setRole(EXISTED_USER.getRole());
//
//            int duration = 3600; // 1h
//            String token = jwtProvider.generateToken(email, duration);
//
//            authDto.setAccessToken(token);
//            authDto.setExpireTime(duration);
//
//            return ResponseDTO.setSuccessData("success", authDto);
//
//        } catch (Exception e) {
//            return ResponseDTO.setFailed("처리 중 문제가 발생했습니다. 잠시 후 다시 시도해주세요.");
//        }
//    }
//
//    public UserDTO updateToken(UserDTO dto, String refreshToken) {
//        dto.setToken(refreshToken);
//        return dto;
//    }

}
