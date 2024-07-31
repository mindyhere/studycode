package com.demo.studycode.service;

import com.demo.studycode.dto.AuthDTO;
import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.model.Auth;
import com.demo.studycode.model.User;
import com.demo.studycode.repository.AuthRepository;
import com.demo.studycode.repository.UserRepository;
import com.demo.studycode.security.CustomUserDetails;
import com.demo.studycode.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

//    public User saveUser(User user) {
//        user.setPasswd(passwdEncoder.encode(user.getPasswd()));
//        return userRepository.save(user);
//    }
//
//    public UserDTO findByEmail(String email) {
//        Optional<User> user= this.userRepository.findByEmail(email);
//        return new UserDTO(user);
//    }

    public AuthDTO login(AuthDTO dto) {
        // 이메일, 비밀번호 확인
        Optional<User> user = this.userRepository.findByEmail(dto.getEmail());
        if (!passwdEncoder.matches(dto.getPasswd(), user.get().getPasswd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다." );
        }

        // 토큰 생성
        String accessToken = this.jwtProvider.generateAccessToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.get().getPasswd()));
        String refreshToken = this.jwtProvider.generateRefreshToken(
                new UsernamePasswordAuthenticationToken(new CustomUserDetails(user), user.get().getPasswd()));

//         Auth entity가 있을 경우, 토큰 업데이트
        if (this.authRepository.findByUser(user)) {
            user.get().getAuth().updateAccessToken(accessToken);
            user.get().getAuth().updateRefreshToken(refreshToken);
            return new AuthDTO(user.get().getAuth());
        }

//        // IF NOT EXISTS AUTH ENTITY, SAVE AUTH ENTITY AND TOKEN
//        Auth auth = this.authRepository.save(Auth.builder()
//                .user(user)
//                .tokenType("Bearer")
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build());
//        return new AuthResponseDto(auth);
    }

//    /** 회원가입 */
//    @Transactional
//    public void signup(UserRequestDto requestDto) {
//        // SAVE USER ENTITY
//        requestDto.setRole(Role.ROLE_USER);
//        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
//        this.userRepository.save(requestDto.toEntity());
//    }
//
//    /** Token 갱신 */
//    @Transactional
//    public String refreshToken(String refreshToken) {
//        // CHECK IF REFRESH_TOKEN EXPIRATION AVAILABLE, UPDATE ACCESS_TOKEN AND RETURN
//        if (this.jwtTokenProvider.validateToken(refreshToken)) {
//            Auth auth = this.authRepository.findByRefreshToken(refreshToken).orElseThrow(
//                    () -> new IllegalArgumentException("해당 REFRESH_TOKEN 을 찾을 수 없습니다.\nREFRESH_TOKEN = " + refreshToken));
//
//            String newAccessToken = this.jwtTokenProvider.generateAccessToken(
//                    new UsernamePasswordAuthenticationToken(
//                            new CustomUserDetails(auth.getUser()), auth.getUser().getPassword()));
//            auth.updateAccessToken(newAccessToken);
//            return newAccessToken;
//        }
//
//        // IF NOT AVAILABLE REFRESH_TOKEN EXPIRATION, REGENERATE ACCESS_TOKEN AND REFRESH_TOKEN
//        // IN THIS CASE, USER HAVE TO LOGIN AGAIN, SO REGENERATE IS NOT APPROPRIATE
//        return null;
//    }

}
