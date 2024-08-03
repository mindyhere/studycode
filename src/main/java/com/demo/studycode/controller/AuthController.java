package com.demo.studycode.controller;

import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/*")
public class AuthController {

    @Autowired
    private AuthService authService;

    /* 회원가입 API */
    @PostMapping("signUp")
    public ResponseEntity signUp(@RequestBody UserDTO request) {

        Long id = authService.signUp(request);
        return ResponseEntity.status(HttpStatus.OK).body(id);

    }

    /* 로그인 API */
    @PostMapping("signIn")
    public ResponseEntity signIn(@Valid @RequestBody UserDTO request) {

        System.out.println("** 이건 콘솔로그: "+request);
        String token = authService.signIn(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);

    }

}
