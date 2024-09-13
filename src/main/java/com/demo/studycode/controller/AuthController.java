package com.demo.studycode.controller;

import com.demo.studycode.dto.UserDTO;
import com.demo.studycode.service.AuthService;
import com.demo.studycode.service.FileService;
import com.demo.studycode.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        System.out.println("==map==/n" + map);
        UserDTO dto = new UserDTO();
        dto.setUserid(map.get("userid").toString());
        dto.setPasswd(map.get("passwd").toString());
        dto.setName(map.get("name").toString());
        dto.setEmail(map.get("email").toString());
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
        request.setUserid(map.get("userid").toString());
        request.setPasswd(map.get("passwd").toString());

        String token = authService.signIn(request);
        return ResponseEntity.status(HttpStatus.OK).body(token);

    }

    /* 아이디 중복확인 API */
    @GetMapping("check/id/{id}")
    public ResponseEntity checkUserid(@PathVariable(name = "id") String id) {

        try {
            if (authService.checkUserid(id)) {
                return ResponseEntity.status(HttpStatus.OK).body(true);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    /* 이메일 인증 API */
    @GetMapping("check/email/{email}")
    public ResponseEntity checkEamil(@PathVariable(name = "email") String email) {

        try {
            String result = authService.checkUserEmail(email);
            System.out.println(result);
            if ((result.equals("fail"))) {
                throw new RuntimeException("메일 발송실패");
            } else if ((result.equals("exits"))) {
                return ResponseEntity.status(HttpStatus.OK).body("exits");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }

    }

    /* 아이디 찾기 API */
    @GetMapping("find/id/{email}/{phone}")
    public ResponseEntity findUserEmail(@PathVariable(name = "email") String email, @PathVariable(name = "phone") String phone) {
        try {
            UserDTO dto = userService.findUserid(email, phone);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("처리 중 문제가 발생했습니다.");
        }
    }

    /* 비밀번호 찾기 API - 임시 비밀번호 발송 */
    @PostMapping("find/pwd/{userid}")
    public ResponseEntity resetPasswd(@PathVariable(name = "userid") String userid, @RequestBody Map<String, Object> map) {
        System.out.println(map);
        try {
            String result = userService.resetPasswd(map);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
