package com.demo.studycode.controller;

import com.demo.studycode.dto.MailDTO;
import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.service.AuthService;
import com.demo.studycode.service.FileService;
import com.demo.studycode.service.MailService;
import com.demo.studycode.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    /* 회원가입 API */
    @PostMapping("signUp")
    public ResponseEntity signUp(@RequestParam Map<String, Object> map, @RequestParam(name = "photo", required = false) MultipartFile photo, HttpServletRequest request) {

        UserDTO dto = new UserDTO();
        dto.setEmail((String) map.get("email"));
        dto.setPasswd(map.get("passwd").toString());
        dto.setName(map.get("name").toString());
        dto.setPhone(map.get("phone").toString());

        if (photo != null && !photo.isEmpty()) {
            try {
                String fileName = fileService.uploadProfile(photo);
                dto.setProfile(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            authService.signUp(dto);
            return ResponseEntity.status(HttpStatus.OK).body("success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }

    }

    /* 로그인 API */
    @PostMapping("signIn")
    public ResponseEntity signIn(@RequestBody Map<String, Object> map) {
        System.out.println("map: " + map);

        UserDTO request = new UserDTO();
        request.setEmail((String) map.get("email"));
        request.setPasswd(map.get("passwd").toString());

        String token = authService.signIn(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);

    }

    /* 이메일 인증 API */
    @GetMapping("check/{email}")
    public ResponseEntity signIn(@PathVariable(name = "email") String userEmail) {

        try {
            String result = authService.checkUserEmail(userEmail);
            System.out.println(result);
            if ((result.equals("fail"))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증코드 발송에 실패했습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용 중인 이메일입니다.");
        }

    }

    /* 이메일(아이디) 찾기 API */
    @GetMapping("find/{name}/{phone}")
    public ResponseEntity findUserEmail(@PathVariable(name = "name") String name, @PathVariable(name = "phone") String phone) {
        try {
            String result = userService.findUserEmail(name, phone);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
        }
    }

    /* 비밀번호 찾기 API - 임시 비밀번호 발송 */
    @PostMapping("find/{email}")
    public ResponseEntity resetPasswd(@PathVariable(name = "email") String email, @RequestParam Map<String, Object> map) {

        try {
            String result = userService.resetPasswd(map);

            if ((result.equals("fail"))) {
                throw new Exception("error");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
        }

    }

}
