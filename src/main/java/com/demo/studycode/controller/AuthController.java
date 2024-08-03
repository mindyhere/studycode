package com.demo.studycode.controller;

import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.service.AuthService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/*")
public class AuthController {

    @Autowired
    private AuthService authService;

    /* 회원가입 API */
    @PostMapping("signUp")
    public ResponseEntity signUp(@RequestBody Map<String, Object> map, @RequestParam(name = "photo", required = false) MultipartFile photo, HttpServletRequest request) {
//        System.out.println("*** "+ map);
        UserDTO dto = new UserDTO();
        dto.setEmail((String) map.get("email"));
        dto.setPasswd(map.get("passwd").toString());
        dto.setName(map.get("name").toString());
        dto.setPhone(map.get("phone").toString());

        ServletContext application = request.getSession().getServletContext();
        String path = application.getRealPath("static/images/");
        String profile = "-";

        if (photo != null && !photo.isEmpty()) {
            try {
                profile = photo.getOriginalFilename();
                photo.transferTo(new File(path + profile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        dto.setProfile(profile);

        System.out.println("!!! "+ dto.getEmail());
        Long id = authService.signUp(dto);
        return ResponseEntity.status(HttpStatus.OK).body(id);

    }

    /* 로그인 API */
    @PostMapping("signIn")
    public ResponseEntity signIn(@Valid @RequestBody UserDTO request) {

        System.out.println("** 이건 콘솔로그: " + request);
        String token = authService.signIn(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);

    }

}
