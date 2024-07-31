package com.demo.studycode.controller;

import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.repository.UserRepository;
import com.demo.studycode.security.JwtTokenProvider;
import com.demo.studycode.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth/*")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService userService;

    @Autowired
    private JwtTokenProvider jwtProvider;

    /* 회원정보 조회 API */
    @GetMapping("login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String accessToken) {
        String email = this.jwtProvider.getUsernameFromToken(accessToken.substring(7));
        UserDTO userDto = this.userService.findByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

}
