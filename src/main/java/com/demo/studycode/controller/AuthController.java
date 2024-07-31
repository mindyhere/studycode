package com.demo.studycode.controller;

import com.demo.studycode.dto.AuthDTO;
import com.demo.studycode.dto.ResponseDTO;
import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/*")
public class AuthController {
    @Autowired
    private AuthService authService;

    /* 회원가입 API */
    @PostMapping("/signUp")
    public ResponseDTO signUp(@RequestBody UserDTO dto) {
        ResponseDTO<?> result = authService.signUp(dto);
        return result;
    }

    /* 로그인 API */
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody UserDTO dto) {
        ResponseDTO<?> responseDto = authService.signIn(dto);
        return setToken(responseDto);
    }

    // Response 결과에 따라 Header에 Token 설정
    private ResponseEntity<?> setToken(ResponseDTO<?> responseDto) {

        if (responseDto.getResult()) { // success
            // Response -> data의 accessToken 추출 -> Header에 토큰 지정
            AuthDTO authDto = (AuthDTO) responseDto.getData();
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + authDto.getAccessToken())
                    .body(responseDto);
        } else {
            return ResponseEntity.ok().body(responseDto);
        }
    }

}
